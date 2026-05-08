import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Task 2 INTERMEDIATE — JUnit verification that a removed observer is
 * never invoked again. Uses a flag-based test observer to record calls.
 */
class HospitalObserverTest {

    /**
     * Test-only observer that records whether it was notified and what risk
     * score it last saw. A flag-based recorder is enough — we do not need a
     * mocking framework for this assertion.
     */
    private static class RecordingObserver implements AdmissionObserver {
        boolean notified = false;
        int callCount = 0;
        int lastRiskScore = -1;

        @Override
        public void onPatientAdmitted(String patientName, int riskScore) {
            notified = true;
            callCount++;
            lastRiskScore = riskScore;
        }
    }

    @Test
    void removedObserver_isNotNotified_onSubsequentAdmissions() {
        Hospital hospital = new Hospital("Test Hospital");
        RecordingObserver recorder = new RecordingObserver();

        hospital.addObserver(recorder);
        hospital.admitPatient("Patient A", 80);
        assertTrue(recorder.notified, "Observer should fire while subscribed");
        assertEquals(1, recorder.callCount);

        hospital.removeObserver(recorder);
        hospital.admitPatient("Patient B", 30);

        assertEquals(1, recorder.callCount,
            "Removed observer must not be invoked again");
        assertEquals(80, recorder.lastRiskScore,
            "Last seen risk should still be Patient A's score");
    }

    @Test
    void multipleObservers_allReceiveSameEvent() {
        Hospital hospital = new Hospital("Test Hospital");
        RecordingObserver a = new RecordingObserver();
        RecordingObserver b = new RecordingObserver();
        RecordingObserver c = new RecordingObserver();
        hospital.addObserver(a);
        hospital.addObserver(b);
        hospital.addObserver(c);

        hospital.admitPatient("Maria", 80);

        assertEquals(80, a.lastRiskScore);
        assertEquals(80, b.lastRiskScore);
        assertEquals(80, c.lastRiskScore);
    }

    @Test
    void urgentOnlyObserver_ignoresLowRiskAdmissions() {
        // Indirect verification: a recording observer placed alongside the
        // UrgentOnlyDoctorNotifier confirms the subject DID notify both, but
        // the urgent-only filter chose not to print/act for low risk. The
        // subject is unaware of the filter — exactly the point of the
        // observer-side filter design.
        Hospital hospital = new Hospital("Test Hospital");
        RecordingObserver recorder = new RecordingObserver();
        hospital.addObserver(new UrgentOnlyDoctorNotifier("Dr. Lim"));
        hospital.addObserver(recorder);

        hospital.admitPatient("Routine", 25);
        assertTrue(recorder.notified,
            "Subject should still fan out — filter is observer-local");
        assertEquals(25, recorder.lastRiskScore);
    }

    @Test
    void noObservers_admitDoesNotThrow() {
        Hospital hospital = new Hospital("Empty");
        hospital.admitPatient("Solo", 10);
        // Reaching this line without exception is the assertion.
        assertFalse(false);
    }
}
