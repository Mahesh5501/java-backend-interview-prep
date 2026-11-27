# System Design Roadmap (30+ LPA prep)

**Audience:** ~5.5 YOE Java backend engineer  
**Goal:** Explain designs clearly in 45–60 minute interviews (staff-leaning roles).

```text
SystemDesign/
├── System_Design_Roadmap.md     ← you are here
└── notes/
    ├── 00_How_to_Approach_System_Design.md   ← start here (framework)
    ├── 01_Core_Building_Blocks.md            ← vocabulary + patterns
    ├── 02_CAP_Consistency_Availability.md
    ├── 03_SQL_vs_NoSQL.md
    ├── 04_Caching.md
    ├── 05_Message_Queues.md
    ├── 06_URL_Shortener.md
    ├── 07_Rate_Limiter.md
    ├── 08_Chat_Messaging.md
    ├── 09_News_Feed.md
    └── 10_Design_a_Payment_or_Wallet_System.md
```

## Study order (4–6 weeks)

| Week | Focus | Files | Outcome |
|------|--------|-------|---------|
| 1 | Framework + building blocks | `00`, `01` | You can open any design question with confidence |
| 2 | Theory you will reuse | `02`–`05` | CAP, DB choice, cache, queue trade-offs |
| 3 | Classic designs (part 1) | `06`, `07` | Short URL + rate limiter end-to-end |
| 4 | Classic designs (part 2) | `08`, `09` | Real-time + feed / fan-out |
| 5–6 | Money + mocks | `10` + redo `06`–`10` aloud | Wallet basics + 2 full timed mocks |

**Daily rhythm (45–60 min):**

1. Read one note (15 min)
2. Draw the ASCII diagram on paper (10 min)
3. Speak the “say this aloud” script once (2 min)
4. Answer follow-up Q&A without looking (10 min)
5. Optional: one mock with a friend or record yourself (30 min)

## What interviewers expect at 30+ LPA

- You **clarify** before you draw (not a silent architect).
- You **estimate** scale with simple math (orders of magnitude, not Excel).
- You **name trade-offs** (latency vs consistency, cost vs complexity).
- You **deep-dive** 2–3 components when asked (not every box).
- You connect to **Java reality**: threads, connection pools, idempotency, transactions.

## Checklist before each mock

- [ ] Functional vs non-functional requirements stated out loud
- [ ] DAU / QPS / storage back-of-envelope
- [ ] One diagram: client → LB → app → cache → DB → queue
- [ ] APIs + data model sketched
- [ ] Bottleneck named + one scaling lever
- [ ] 60–90 sec closing summary

## After you finish this track

- Add company-specific designs (search, video, ride-hailing) using the same `00` template.
- Pair with **DSA** track in sibling folder `DSA/`.
- Re-read `00` and `01` weekly — they are your cheat sheet in the interview room.

---

*Last updated: Nov 2025 — beginner-friendly, interview-spoken language.*