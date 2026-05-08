import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Task 3 — Strategy pattern tests.
 *
 *  - Standard vs Conservative produce different scores for the same input.
 *  - setStrategy swaps the algorithm at runtime.
 *  - PediatricRiskStrategy slots in with NO modification to
 *    PatientAdmissionV2 — proving the Open/Closed Principle.
 */
class PatientAdmissionV2Test {

    private static final double TEMP = 38.7;
    private static final int AGE = 62;

    @Test
    void sameInputs_differentStrategies_produceDifferentScores() {
        PatientAdmissionV2 standard = new PatientAdmissionV2(new StandardRiskStrategy());
        PatientAdmissionV2 conservative =
            new PatientAdmissionV2(new ConservativeRiskStrategy());

        int s = standard.calculateRiskScore(TEMP, AGE);
        int c = conservative.calculateRiskScore(TEMP, AGE);

        assertEquals(25, s, "Standard: 38.7 °C is in the mid band (+25), age 62 is below 65 (+0)");
        assertEquals(80, c, "Conservative: 38.7 °C is in the high band (+50), age 62 is over 60 (+30)");
        assertNotEquals(s, c, "Strategies must produce different scores for this input");
    }

    @Test
    void setStrategy_atRuntime_changesBehaviour() {
        PatientAdmissionV2 admission = new PatientAdmissionV2(new StandardRiskStrategy());
        int before = admission.calculateRiskScore(TEMP, AGE);

        admission.setStrategy(new ConservativeRiskStrategy());
        int after = admission.calculateRiskScore(TEMP, AGE);

        assertEquals(25, before);
        assertEquals(80, after);
    }

    @Test
    void pediatricStrategy_pluginsIn_withoutModifyingPatientAdmissionV2() {
        // Open/Closed proof — we can use a brand-new strategy class with no
        // edits to PatientAdmissionV2 at all.
        PatientAdmissionV2 admission = new PatientAdmissionV2(new PediatricRiskStrategy());

        // 8yo (under 12 -> +20) with 38.5 °C (>= 38.0 -> +40) = 60
        assertEquals(60, admission.calculateRiskScore(38.5, 8));
        // 14yo (over 12 -> +0) with 36.5 °C (under 38.0 -> +0) = 0
        assertEquals(0, admission.calculateRiskScore(36.5, 14));
        // 5yo (under 12 -> +20) with 36.0 °C (under 38.0 -> +0) = 20
        assertEquals(20, admission.calculateRiskScore(36.0, 5));
    }

    @Test
    void standardStrategy_youngHealthyPatient_returnsZero() {
        PatientAdmissionV2 admission = new PatientAdmissionV2(new StandardRiskStrategy());
        assertEquals(0, admission.calculateRiskScore(36.5, 25));
    }
}
