# Task 3 — Strategy Pattern Notes

## How Hospital, RiskCalculationStrategy, and AdmissionObserver relate

```
                     ┌──────────────────────────────┐
                     │  RiskCalculationStrategy     │   (interface)
                     │  +calculateRisk(temp, age)   │
                     └──────────────┬───────────────┘
                                    │ implemented by
        ┌───────────────────────────┼─────────────────────────────┐
        │                           │                             │
┌───────▼──────────┐  ┌─────────────▼────────────┐   ┌────────────▼────────┐
│ StandardRisk     │  │ ConservativeRisk         │   │ PediatricRisk       │
│ Strategy         │  │ Strategy                 │   │ Strategy            │
└──────────────────┘  └──────────────────────────┘   └─────────────────────┘
                                    ▲
                                    │ has-a (injected)
                                    │
                          ┌─────────┴──────────┐         ┌──────────────────────┐
                          │     Hospital       │ holds  │ AdmissionObserver    │
                          │  +admitPatient(... │────►  │  (interface)          │
                          │   name,temp,age)   │ many  │  +onPatientAdmitted   │
                          └────────────────────┘        └──┬───────────────────┘
                                                           │ implemented by
                                  ┌──────────┬─────────────┼─────────────┬─────────────┐
                            ┌─────▼────┐  ┌──▼───────┐  ┌──▼────────┐  ┌─▼──────────┐  ┌▼────────────────┐
                            │ Doctor   │  │ Billing  │  │ Audit     │  │ WardNurse  │  │ UrgentOnlyDoctor │
                            │ Notifier │  │ Notifier │  │ Notifier  │  │ Notifier   │  │ Notifier         │
                            └──────────┘  └──────────┘  └───────────┘  └────────────┘  └──────────────────┘
```

`Hospital.admitPatient(name, temp, age)`:
1. Asks the injected `RiskCalculationStrategy` to compute a score.
2. Calls `notifyObservers(name, score)` to fan that score out to every
   registered `AdmissionObserver`.

The two patterns are orthogonal: swapping the strategy (e.g. country
guideline) does not touch any observer; adding an observer (push, SMS,
log shipping) does not touch any strategy.

## Think About It — answer

**Stock-trading platform.** A `MarketDataFeed` is the subject; price ticks are events. Subscribers — a charting widget, a moving-average indicator, a stop-loss engine, a trade audit logger — are observers. Each one cares about price changes for its own reason; the feed only knows there is a list of things to call. When a new dashboard module needs prices, it registers as an observer; the feed itself is not touched. This is the same shape as the MediTrack Hospital → notification handlers relationship.
