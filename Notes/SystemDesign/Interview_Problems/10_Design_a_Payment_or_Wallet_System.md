# Design: Payment / Wallet System (High Level)

Educational only — **not** company-specific. Focus on **ledger**, **idempotency**, and **consistency**.

---

## 1. Clarify requirements

### Functional

| In scope | Out of scope (v1) |
|----------|-------------------|
| Wallet per user with balance | FX across currencies |
| Credit (top-up), debit (pay merchant) | Complex lending |
| Transfer wallet A → B | Card network PCI in your DC |
| Transaction history | Chargeback dispute workflow |

### Non-functional

- **Correctness:** no double spend; balance never negative (unless credit line defined)
- **Strong consistency** on balance updates
- **Idempotent** payment APIs
- **Audit:** immutable ledger entries
- **Availability:** 99.95% for reads; writes can queue briefly on incident
- **Scale:** 10M users, **1M payments/day** → ~12/sec avg, **~50/sec** peak

---

## 2. Capacity estimate

- 1M tx/day, ledger row ~200 bytes → **200 MB/day** ledger
- History reads 5× writes → low QPS vs social systems
- **This is a consistency system, not a QPS monster**

---

## 3. High-level diagram

```text
 [Client / Merchant API]
           │
           ▼
      [API Gateway]  (auth, rate limit)
           │
           ▼
   [Payment Service]  (orchestration)
           │
     ┌─────┴─────┬──────────────┐
     ▼           ▼              ▼
 [Wallet Svc] [Ledger DB]   [Payment Provider Adapter]
     │       (SQL, ACID)         (stub / webhook)
     │
     └── idempotency store (Redis or SQL)
```

Async:

```text
Payment completed ──► [Queue] ──► notifications, analytics, search
```

---

## 4. API sketch

```http
POST /v1/wallets/{userId}/topup
Headers: Idempotency-Key: uuid
Body: { "amount": "100.00", "currency": "INR", "provider_ref": "..." }

POST /v1/payments
Headers: Idempotency-Key: uuid
Body: {
  "payer_wallet_id": "w1",
  "payee_wallet_id": "w2",
  "amount": "50.00",
  "currency": "INR"
}
Response: { "payment_id": "pay_abc", "status": "COMPLETED" }

GET /v1/wallets/{userId}/balance
GET /v1/wallets/{userId}/transactions?cursor=...
```

Statuses: `PENDING`, `COMPLETED`, `FAILED`, `REVERSED`

---

## 5. Data model sketch

**Double-entry ledger (best practice)**

**`accounts`** (wallet)

| account_id | user_id | currency | version |

**`ledger_entries`** (append-only)

| entry_id | txn_id | account_id | debit | credit | created_at |

Constraint: per `txn_id`, sum debits = sum credits.

**`transactions`**

| txn_id | type | status | idempotency_key | metadata |

**`idempotency_keys`**

| key | response_body | status | expires_at |

**`balances`** (optional materialized)

| account_id | balance | version |  — updated in **same TX** as ledger rows

---

## 6. Deep dive

### A. One payment flow (transfer)

```text
BEGIN TRANSACTION
  1. Lock payer account row (SELECT FOR UPDATE)
  2. Check balance >= amount
  3. Insert ledger: debit payer, credit payee
  4. Update balances / version
  5. Insert transaction COMPLETED
COMMIT
```

**Say:** “All money movement is one DB transaction with row locks on involved wallets.”

### B. Idempotency

```text
Client retries same Idempotency-Key
  → if key exists: return stored response (201 same body)
  → else: process and store response
```

Prevents **double charge** on network timeout.

### C. External top-up (provider)

1. Create `PENDING` transaction
2. Call provider API
3. Webhook confirms → complete ledger in TX
4. Webhook idempotent by `provider_event_id`

Never credit wallet **only** from client callback — trust provider webhook + signature.

```text
Client ──► Pending tx ──► Provider
                            │
                       webhook ──► complete ledger (idempotent)
```

### D. Saga vs single TX

- **Single wallet transfer:** one SQL TX is enough
- **Pay merchant + fee + tax:** one TX if one DB; **saga** with compensating refund if cross services

---

## 7. Bottlenecks & scaling

| Risk | Mitigation |
|------|------------|
| Row lock hot wallet | Partition flows; merchant settlement batch |
| DB becomes sole bottleneck | Still OK at modest QPS; optimize indexes; read replicas for history |
| Split brain / duplicate webhook | Unique constraint on `provider_event_id` |
| Reconciliation | Daily batch: ledger vs provider statements |

**Do not** shard wallets day one without need — **correctness first**.

---

## 8. “Say this aloud” (90 sec)

> “This is a ledger-first wallet. Every top-up, payment, or transfer creates balanced double-entry ledger rows in a single ACID transaction, with row-level locks on the affected wallet accounts so we never double-spend or go negative incorrectly. APIs require an idempotency key so client retries return the same result without duplicating money movement. External top-ups stay pending until a signed provider webhook confirms; we dedupe webhooks by event ID. Read path serves balance from a materialized column or sum of ledger; history is paginated from the ledger table. Async queues send receipts and analytics after commit. The trade-off is we favor strong consistency and auditability over extreme write QPS—we can add read replicas and careful sharding by account ID later if volume demands it.”

---

## 9. Follow-up Q&A

**Q: Event sourcing vs ledger table?**  
A: Ledger is event-like append-only; event sourcing rebuilds state from stream — both valid; ledger table is simpler to explain.

**Q: Negative balance?**  
A: Business rule — separate credit line account or reject debit.

**Q: Multi-currency?**  
A: Separate account per currency; no silent FX in v1.

**Q: Exactly-once payment?**  
A: Idempotent API + unique business keys + transactional outbox to downstream.

**Q: PCI?**  
A: Never store raw card; use tokenization via provider; scope reduction.

**Q: How Java?**  
A: `@Transactional` service method, pessimistic lock on account repo, `BigDecimal` for money, never `double`.