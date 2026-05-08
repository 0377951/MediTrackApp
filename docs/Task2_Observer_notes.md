# Task 2 — Observer Pattern Notes

## Class relationships

```
        ┌──────────────────────┐
        │  AdmissionSubject    │   (interface)
        │  +addObserver        │
        │  +removeObserver     │
        │  +notifyObservers    │
        └──────────┬───────────┘
                   │ implements
        ┌──────────▼───────────┐
        │      Hospital        │
        │  - List<Observer>    │
        │  +admitPatient(...)  │
        └──────────┬───────────┘
                   │ holds many
        ┌──────────▼───────────────────┐
        │      AdmissionObserver       │  (interface)
        │  +onPatientAdmitted          │
        └──┬──────────┬──────────┬─────┘
           │          │          │
   ┌───────▼──┐  ┌────▼─────┐  ┌─▼────────────────────┐
   │ Doctor   │  │ Billing  │  │ UrgentOnlyDoctor      │
   │ Notifier │  │ Notifier │  │ Notifier (filtered)   │
   └──────────┘  └──────────┘  └───────────────────────┘
   (also: AuditNotifier, WardNurseNotifier)
```

## Challenge: where should the urgent filter live?

**Implemented in the observer (`UrgentOnlyDoctorNotifier`).** Trade-offs:

- **Filter in observer (chosen).** The subject stays oblivious to filtering rules. Every observer can have its own policy — billing wants every admission, the doctor only wants urgent ones. Adding a "VIP-only billing observer" tomorrow needs zero changes to `Hospital`.
- **Filter in subject.** The hospital would need to track per-observer thresholds (e.g. `addObserver(observer, minRisk)`). That re-couples `Hospital` to a concept (severity) it should not care about, and forces every future observer to fit one filtering schema. It also breaks the Open/Closed Principle: a new filter type means editing `Hospital`.

The trade-off in favour of the subject filter is *efficiency at scale* — if there are 10 000 observers and each event matches few of them, doing the filtering once in the subject avoids a fan-out. But MediTrack has dozens of observers at most, so the simplicity and OCP-friendliness of the observer-side filter wins.

## Think About It — answer

Without the Observer pattern, adding a push-notification handler would mean editing `Hospital.admitPatient()` to add another inline call. Every existing notification-related class becomes a co-author of the change, and every test that exercises admission needs review.

With the Observer pattern, the same change is a single new class (`PushNotifier implements AdmissionObserver`) plus one `addObserver(...)` call at wiring time. **Zero existing classes change.** That is the whole point of the pattern: extension via *registration*, not modification.
