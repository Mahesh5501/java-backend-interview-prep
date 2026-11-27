# Design: Chat / Messaging (1:1 and groups)

Blend of **real-time delivery**, **history**, and **presence**. Medium-hard mock.

---

## 1. Clarify requirements

### Functional

| In scope | Out of scope (v1) |
|----------|-------------------|
| Send/receive text messages | E2E encryption |
| 1:1 and group chats | Voice/video |
| Message history (pagination) | Message edit/delete sync (v2) |
| Online/offline delivery | Full-text search across all chats |

### Non-functional

- **Users:** 50M DAU
- **Messages:** 50 msgs/user/day avg
- **Latency:** deliver online user < 500 ms
- **Durability:** messages never lost once ACKed to sender
- **Ordering:** per conversation causal order is enough

---

## 2. Capacity estimate

- 50M × 50 = **2.5B messages/day** → **25K msgs/sec** avg → **~75K/sec** peak
- Message size ~500 bytes → **1.25 TB/day** raw → compress + cold archive

Connections:

- 10% concurrent online → **5M WebSockets** (need many connection servers)

---

## 3. High-level diagram

```text
 [Mobile/Web]
      │
      ├── REST: history, send (fallback)
      │
      └── WebSocket ──► [Connection servers]
                              │
                              ▼
                      [Message Router]
                         │        │
            ┌────────────┘        └────────────┐
            ▼                                  ▼
      [Message Service]                  [Presence Redis]
            │
            ├──► [Message DB] (time-ordered)
            │
            └──► [Queue] ──► push workers ──► WS fan-out
```

Offline: store + push notification queue.

---

## 4. API sketch

```http
POST /v1/conversations/{id}/messages
Body: { "client_msg_id": "uuid", "text": "hello" }
Response: { "message_id": 987, "server_ts": ... }

GET /v1/conversations/{id}/messages?before_id=987&limit=50

WebSocket:
  → { "type": "send", "conversation_id", "client_msg_id", "text" }
  ← { "type": "ack", "message_id" }
  ← { "type": "message", "payload": ... }
```

---

## 5. Data model sketch

**`conversations`**

| id | type (dm/group) | created_at |

**`conversation_members`**

| conversation_id | user_id | joined_at |

**`messages`** (shard by `conversation_id`)

| message_id | conversation_id | sender_id | body | server_ts |

Index: `(conversation_id, message_id DESC)` for pagination.

**`user_inbox`** (optional for fan-out-on-read model)

| user_id | conversation_id | last_message_id | unread_count |

---

## 6. Deep dive

### A. Real-time path

1. Sender POST or WS → Message Service
2. Persist message (DB)
3. Lookup online recipients in **Presence** (Redis: `user_id → connection_server_id`)
4. Router pushes to correct connection server
5. ACK to sender after durable write (not after deliver—product choice)

```text
Sender ──► Save DB ──► Publish event
                         │
            ┌────────────┼────────────┐
            ▼            ▼            ▼
        User B online  User C offline
        WS push        FCM/APNs queue
```

### B. Message ordering

- `message_id` snowflake (time-ordered)
- Per conversation total order
- Client sends `client_msg_id` for idempotent retry

### C. Group fan-out

- Small group: push list of members from DB
- Large group: **don't** fan-out to 10K sockets sync — cap group size v1 or use pull on open

---

## 7. Bottlenecks & scaling

| Bottleneck | Scale |
|------------|-------|
| WebSocket connections | Dedicated gateway fleet; sticky by connection |
| Hot conversation | Serialize writes per conversation_id shard |
| History read | Cache recent page; DB partition by conversation_id |
| Presence storms | Heartbeat every 30s; batch updates |

---

## 8. “Say this aloud” (80 sec)

> “Clients connect via WebSocket to a connection tier for real-time delivery, and use REST for history backfill. Sending a message hits the message service, which durably stores it keyed by conversation, then looks up recipient presence in Redis. Online users get a push through a message router to the right connection server; offline users get mobile push via a queue. We use time-ordered message IDs for pagination and ordering. The heavy load is concurrent connections and write QPS to the message store, so we shard by conversation ID and scale connection servers horizontally. We accept that large groups need limits or hybrid pull to avoid fan-out explosions.”

---

## 9. Follow-up Q&A

**Q: At-least-once delivery?**  
A: Yes; client dedupes with `client_msg_id`.

**Q: Read receipts?**  
A: Separate lightweight table or WS events; eventual OK.

**Q: Media messages?**  
A: Upload to object storage; message body has URL + thumbnail.

**Q: Multi-device?**  
A: Fan-out to all device connections for user; same inbox cursor per user.