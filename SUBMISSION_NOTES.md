# CSC61204 — Week 2 Lab Submission Notes

**Module:** Software Construction
**Lab:** Specification and Unit Testing with JUnit 5
**Project:** MediTrackApp

> Paste the relevant pieces of this file into your `0377951_Week2Lab.docx` cover document, attach screenshots where indicated, and push the project to GitHub.

---

## Test run summary

```
40 tests found · 40 tests successful · 0 failed
```

| Test class                          | Tests | Covers                                              |
| ----------------------------------- | ----: | --------------------------------------------------- |
| `SanityTest`                        |     3 | Task 1: lifecycle & first passing test              |
| `PatientAdmissionTest`              |    32 | Task 2 (beginner+intermediate+challenge), Task 3    |
| `PatientAdmissionIntegrationTest`   |     5 | Task 3 intermediate: `assertAll()` end-to-end flow  |

How to reproduce from the project root:

```bash
javac -d out/production src/PatientAdmission.java
javac -cp lib/junit-platform-console-standalone-1.10.0.jar:out/production \
      -d out/test test/SanityTest.java test/PatientAdmissionTest.java \
                  test/PatientAdmissionIntegrationTest.java
java -jar lib/junit-platform-console-standalone-1.10.0.jar execute \
     --class-path out/production:out/test \
     --select-class SanityTest \
     --select-class PatientAdmissionTest \
     --select-class PatientAdmissionIntegrationTest \
     --details=tree
```

---

## Task 1 — JUnit Setup

### Intermediate: lifecycle ordering

For the three `SanityTest` methods the printed lines were:

```
[Before] Setting up test
[After]  Cleaning up
[Before] Setting up test
[Test]   firstTest
[After]  Cleaning up
[Before] Setting up test
[Test]   secondTest
[After]  Cleaning up
```

**Why:** JUnit 5 instantiates a fresh test class per test method by default and runs the lifecycle in the order `@BeforeEach → @Test → @AfterEach`. The cycle repeats for every `@Test`. `junitIsConfiguredCorrectly` has no `println` of its own, so its slice shows only the before/after pair. The point of `@BeforeEach`/`@AfterEach` is precisely this isolation: each test starts from a known state and leaves no residue for the next one.

### Challenge research

**Q1 — `@BeforeEach` vs `@BeforeAll`.**
`@BeforeEach` runs before *every* test method (the default per-method instance is fresh too); `@BeforeAll` runs once before *any* test in the class and must be `static`. In MediTrack we'd use `@BeforeEach` to construct a fresh `PatientAdmission` (so one test mutating state can't poison another), and `@BeforeAll` for expensive one-time setup that the tests only read — e.g., loading a static reference table of ICD codes from disk.

**Q2 — Testing pyramid.**

```
        ╱╲          UI / E2E tests   (few, slow, brittle)
       ╱──╲
      ╱    ╲        Integration       (some)
     ╱──────╲
    ╱        ╲      Unit tests        (many, fast, isolated)
   ╱──────────╲
```

The pyramid says most of your tests should be small unit tests at the base, fewer integration tests in the middle, and a thin tip of UI/E2E tests. Inversion (an "ice-cream cone") is a smell: slow E2E tests dominate the build, feedback gets sluggish, and bugs surface late.

**Q3 — Why 80% coverage, and what it does not guarantee.**
80% is a *floor* that forces the team to write tests for the obvious paths and stops new untested code from sneaking in. What it doesn't guarantee:

- That the assertions are meaningful — a test that calls a method but never asserts anything still counts as covered.
- That edge cases are tested (off-by-one, nulls, empty collections, time zones).
- That branches are covered — line coverage of 100% can still miss `if`/`else` combinations.
- That requirements are met — coverage measures code, not specification.
- That the code is correct under concurrency, on production data, or against integration boundaries.

### Think About It

*Testing that code runs vs testing that code is correct.* "Runs" only means it didn't throw — `calculateRiskScore(40.0, 70)` returning `42` would still "run". "Correct" means the output matches the specification — the test `assertEquals(80, score)` is what enforces correctness. In MediTrack: code that runs but is incorrect could quietly assign a 95-year-old with a 40°C fever to `ROUTINE` priority and someone gets harmed.

---

## Task 2 — Basic Unit Tests

### Beginner — 9 passing tests

All 9 tests in `PatientAdmissionTest` (the three groups: `calculateRiskScore`, `getAdmissionPriority`, `isValidPatientName`) pass — see test output above.

### Intermediate — 4 boundary tests

Added in the same class:

| Test | Boundary | Expected |
| ---- | -------- | -------- |
| `calculateRiskScore_temperatureExactly39_5_addsFiftyToScore` | temp == 39.5 | 50 |
| `calculateRiskScore_ageExactly65_addsThirtyToScore` | age == 65 | 30 |
| `isValidPatientName_exactly100Characters_returnsTrue` | length == 100 | `true` |
| `isValidPatientName_101Characters_returnsFalse` | length == 101 | `false` |

These are the off-by-one fault-zones — if a developer accidentally wrote `> 39.5` instead of `>= 39.5`, the first test would fail immediately.

### Challenge — TDD: `isEligibleForPriorityCare`

**Specification (restated):** `isEligibleForPriorityCare(int age, boolean hasChronicCondition)` returns `true` if age ≥ 65 OR `hasChronicCondition` is `true`; otherwise `false`.

**Cycle followed:**

1. **RED** — wrote the 5 tests in `PatientAdmissionTest` ("`isEligibleForPriorityCare_*`") *before* the method existed. Compilation failed (method missing) — the canonical first red.
2. **GREEN** — added the minimum implementation: `return age >= 65 || hasChronicCondition;` — all 5 tests pass.
3. **REFACTOR** — the one-liner is already as clean as it gets; no further change. All previously passing tests still pass.

Five test cases cover both branches and the boundary at 65:

- young + healthy → `false`
- young + chronic → `true` (chronic branch)
- elderly + healthy → `true` (age branch)
- age == 65 + healthy → `true` (boundary)
- elderly + chronic → `true` (both branches)

> 📸 **Screenshot 1 (RED)** — paste your IntelliJ test runner screenshot showing the failing/uncompilable test before you added the method.
> 📸 **Screenshot 2 (GREEN)** — paste the same runner showing all five `isEligibleForPriorityCare_*` tests green.

### Think About It

*What happens to other tests when one fails?* JUnit 5 isolates failures: every other `@Test` still runs and is reported independently. That matters on a team because a single broken assertion doesn't hide ten other regressions, and the report tells you whether you have one bug or a class of bugs. If failures aborted the run, fixing tests would become a slow one-at-a-time loop.

---

## Task 3 — Advanced Testing Techniques

### Beginner — exception + parameterized

- `calculateRiskScore_temperatureBelow30_throwsIllegalArgument` — `assertThrows` confirms the validation guard.
- `calculateRiskScore_temperatureAbove45_throwsIllegalArgument` — same, upper bound.
- `calculateRiskScore_variousInputs_returnsCorrectScore` — `@ParameterizedTest` with `@CsvSource` of 4 rows; the JUnit runner reports each row as a separate sub-test, so a regression on a single boundary is visible without scrolling through nine duplicate methods.

### Intermediate — `assertAll()` integration

`PatientAdmissionIntegrationTest` exercises 5 patient profiles end-to-end:

| Profile                 | Expected score | Expected priority |
| ----------------------- | -------------- | ----------------- |
| Elderly + high fever    | 80             | URGENT            |
| Middle-aged + elevated  | 40             | MODERATE          |
| Young + healthy         | 0              | ROUTINE           |
| Elderly + normal temp   | 30             | MODERATE (boundary on 30) |
| Young + high fever      | 50             | MODERATE          |

`assertAll` groups the score and priority assertions so a failure on the score doesn't hide a separate failure on the priority — both are reported in the same run.

### Challenge — `formatPatientReport` specification

**Five acceptance criteria (written before the code):**

1. **AC1 — Returns a non-null, non-empty string** for valid inputs.
2. **AC2 — Output format** is exactly `"Patient: <name> | Risk: <score> | Priority: <priority>"`.
3. **AC3 — Whitespace** at the start and end of `name` is trimmed in the output.
4. **AC4 — Risk score** is rendered as an integer (no decimal point or trailing zeros).
5. **AC5 — Priority validation** — only `URGENT`, `MODERATE`, `ROUTINE` are accepted; anything else throws `IllegalArgumentException`.

**Implementation** lives in `PatientAdmission.formatPatientReport`. **Tests** are in `PatientAdmissionTest` and are individually labelled `_AC1` through `_AC5` so the failure message points straight back at the specification clause that broke.

**Edge-case tests** (also in `PatientAdmissionTest`):

- `formatPatientReport_nullName_throwsIllegalArgument`
- `formatPatientReport_negativeRiskScore_throwsIllegalArgument`
- `formatPatientReport_emptyPriority_throwsIllegalArgument`

### Think About It

*100 unit tests pass — does the system work?* No — only that the units work in isolation, against the assumptions the unit tests encoded. MediTrack still needs:

- **Integration tests** — does the database actually persist what the service layer thinks it does?
- **Contract / API tests** — does the lab-results vendor still send the field shape we expect?
- **End-to-end / UI tests** — can a triage nurse actually complete the admission on the real screen?
- **Performance / load tests** — does the system stay responsive at peak A&E volume?
- **Security tests** — is patient data isolated, encrypted at rest, and audit-logged?
- **Usability / clinical-safety review** — does the workflow match what a clinician under pressure can actually follow?

---

## Reflection (3 tasks × 3 questions)

### Task 1

1. **Learned:** JUnit 5's lifecycle annotations enforce per-test isolation by default — `@BeforeEach`/`@AfterEach` ran around *every* test, not once per class. The console standalone jar makes the dependency setup trivial outside of IntelliJ too.
2. **Challenge:** working out the right Maven coordinate and where IntelliJ stores library entries in the `.iml` file. Resolved by using the all-in-one `junit-platform-console-standalone` jar which bundles Jupiter, Vintage and the launcher.
3. **Real-world link:** real CI servers don't run IntelliJ; the same jar+CLI flow that compiles and runs locally is what GitHub Actions or Jenkins invokes during the build. Setting it up once means the green tick locally equals the green tick on the team's PR.

### Task 2

1. **Learned:** the `method_state_expected` naming convention turns a test report into a readable specification. When `calculateRiskScore_temperatureExactly39_5_addsFiftyToScore` fails, you know immediately what broke without opening the file.
2. **Challenge:** picking inputs that make the boundary visible (39.5 vs 39.4 etc.) — the inclusive vs exclusive comparison was easy to get wrong on first read. The boundary tests caught one such off-by-one in my draft.
3. **Real-world link:** patient-safety-critical code is exactly where boundary bugs hurt — a single `>` instead of `>=` could mis-triage a borderline patient. Boundary tests are the cheapest insurance against that.

### Task 3

1. **Learned:** `assertThrows` keeps exception tests assertive (it returns the thrown exception so you can also assert on its message), and `@ParameterizedTest` collapses ten near-duplicate test methods into one method with a data table — far less to maintain.
2. **Challenge:** writing the `formatPatientReport` acceptance criteria *before* the code felt slow at first, but it forced me to decide what "trim", "integer", and "valid priority" actually meant before any keystroke of implementation. The implementation then dropped out almost mechanically.
3. **Real-world link:** in production, bad input is the rule, not the exception. Throwing a clear `IllegalArgumentException` at the boundary fails fast and loud, which is far better than silently producing a malformed report that some downstream consumer then mis-interprets.

---

## Files added in this commit

```
lib/junit-platform-console-standalone-1.10.0.jar   (JUnit 5 runner)
src/PatientAdmission.java                          (production code)
test/SanityTest.java                               (Task 1)
test/PatientAdmissionTest.java                     (Task 2 + Task 3)
test/PatientAdmissionIntegrationTest.java          (Task 3 intermediate)
SUBMISSION_NOTES.md                                (this file)
```

`MediTrackApp.iml` updated to mark `test/` as test source root and add the JUnit jar as a module library.
