SOUL — RFID Tunnel / RFID Gateway
=================================

What this system *is*, not only what it *does*.

---

**Essence**

A single brain between many bodies: Impinj R220 readers on the network speak LLRP through the Octane SDK; this gateway translates that stream into something the rest of the world already understands — HTTP, JSON, WebSockets, PostgreSQL, and HTML. It does not replace the reader; it *orchestrates* it — connect, configure antennas, start and stop inventory, survive disconnects, and remember what passed through the field.

**Metaphor**

Think of a **tunnel**: raw radio and vendor protocol go in one side; out the other side come durable events (who, where, when, how strong) and live signals for dashboards and automations. The tunnel is narrow on purpose: one place to tune RSSI thresholds, session semantics, timeouts, and reconnection — so applications do not each re-implement reader physics and Impinj quirks.

**Core invariants**

1. **Readers are configured, not hardcoded.** Identity, host, enablement, and antennas live in the database and UI/API.
2. **Physical state and logical state are paired.** “Connected” and “reading” are tracked in persistence; the SDK drives the truth, the DB reflects it for operators.
3. **Tags become events.** An EPC at an antenna with RSSI/phase/timestamp is the atomic unit stored and broadcast.
4. **Sessions are optional semantics on top of inventory.** When a session is active, the gateway may treat each EPC as “first sighting only” for that session — useful for scan workflows without spamming duplicates. Without a session, every read can still be recorded (depending on listener logic).
5. **Failure is expected.** Connection loss triggers WebSocket signals and scheduled reconnect; the system is built for long-running plant floors, not demo laptops alone.

**What success feels like**

Operators start a session (one reader or a whole group), tags appear in the UI or client in real time, history is queryable, and when a cable fails the same service eventually glues the reader back without redeploying the whole stack.

**What this is not**

Not a full WMS, not identity resolution for EPCs into SKUs (unless you layer that on), not a certification of Impinj behavior — it is the **integration spine** for RFID at the edge of your own domain logic.

---

Carry this file when extending the codebase: new features should respect the tunnel — one orchestration point, clear event contracts, and honest handling of disconnects.
