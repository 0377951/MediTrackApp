# Task 1 — Challenge: SOLID Violations Hunt

## Original code

```java
public class HospitalSystem {
    public void admitPatient(String name) { /* ... */ }
    public void generateInvoice(String name) { /* ... */ }
    public void sendSMS(String message) { /* ... */ }
    public void connectToDatabase() { /* ... */ }
    public void printReport(String report) { /* ... */ }
    public double calculateInsurance(String plan, double amount) { /* ... */ }
}
```

## Violations

| # | Principle | Where | Why it's violated |
|---|-----------|-------|-------------------|
| 1 | **SRP** — Single Responsibility | The whole class | Six unrelated reasons to change live in one class: admissions, billing, SMS messaging, database access, printing, and insurance pricing. A change to any one of these forces edits and re-deployment of the others. |
| 2 | **OCP** — Open/Closed | `sendSMS`, `calculateInsurance` | Adding a new notification channel (push/email) or a new insurance plan means modifying this class. There is no abstraction to extend. |
| 3 | **DIP** — Dependency Inversion | `sendSMS`, `connectToDatabase` | High-level orchestration depends directly on concrete I/O (an SMS gateway, a specific database). It should depend on a `Notifier` and a `DatabaseConnection` abstraction. |
| 4 | **ISP** — Interface Segregation | The class as a public API | Any client that only needs to print a report still depends on insurance and SMS methods it never calls. A small focused interface per role would avoid this. |

LSP is not directly violated because there is no inheritance here, but the SRP/OCP issues make any future subclass likely to violate it.

## Corrected design

The single class is split into focused services that depend on abstractions:

- `AdmissionService` — admits patients; depends on `Notifier`.
- `BillingService` — generates invoices and calculates insurance via an injected `InsurancePlan` strategy.
- `DatabaseConnection` — persistence concern.
- `ReportPrinter` — printing concern.
- `Notifier` (existing interface) — `EmailNotifier`, `SmsNotifier`, …

Adding a new channel (e.g. push) now means writing one new `Notifier` implementation. Nothing existing is edited. Adding a new insurance plan means writing one new `InsurancePlan` lambda or class. Same story.

See: `src/AdmissionService.java`, `src/BillingService.java`, `src/DatabaseConnection.java`, `src/ReportPrinter.java`, `src/Notifier.java`, `src/EmailNotifier.java`, `src/SmsNotifier.java`.

## Think About It — answer

With the OCP-compliant Notifier design, adding a push notification requires **modifying zero existing classes** — you write one new `PushNotifier implements Notifier` and register it where channels are wired up. As the system grows this number is what bounds the *blast radius* of every change: zero modifications means zero regressions in code that already works and zero re-testing of unrelated callers.
