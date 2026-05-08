import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration-style tests that exercise the full admission flow:
 * calculateRiskScore -> getAdmissionPriority for several patient profiles.
 *
 * Each test uses assertAll() so every assertion runs even if an earlier one
 * fails; this gives a complete picture of which step in the flow broke.
 */
class PatientAdmissionIntegrationTest {

    private PatientAdmission admission;

    @BeforeEach
    void setUp() {
        admission = new PatientAdmission();
    }

    @Test
    void fullAdmissionFlow_elderlyHighFever_urgentPriority() {
        // 39.5+ -> +50; 65+ -> +30; total 80 -> URGENT
        int score = admission.calculateRiskScore(40.0, 72);
        assertAll(
                () -> assertEquals(80, score),
                () -> assertEquals("URGENT", admission.getAdmissionPriority(score))
        );
    }

    @Test
    void fullAdmissionFlow_middleAgedElevatedTemp_moderatePriority() {
        // 37.5..39.5 -> +25; 50..64 -> +15; total 40 -> MODERATE
        int score = admission.calculateRiskScore(38.0, 55);
        assertAll(
                () -> assertEquals(40, score),
                () -> assertEquals("MODERATE", admission.getAdmissionPriority(score))
        );
    }

    @Test
    void fullAdmissionFlow_youngHealthyAdult_routinePriority() {
        // Normal temp + young -> 0 -> ROUTINE
        int score = admission.calculateRiskScore(36.8, 25);
        assertAll(
                () -> assertEquals(0, score),
                () -> assertEquals("ROUTINE", admission.getAdmissionPriority(score))
        );
    }

    @Test
    void fullAdmissionFlow_elderlyNormalTemp_moderatePriority() {
        // Normal temp + elderly -> 0 + 30 = 30 -> MODERATE (boundary)
        int score = admission.calculateRiskScore(37.0, 68);
        assertAll(
                () -> assertEquals(30, score),
                () -> assertEquals("MODERATE", admission.getAdmissionPriority(score))
        );
    }

    @Test
    void fullAdmissionFlow_youngHighFever_moderatePriority() {
        // High temp + young -> 50 + 0 = 50 -> MODERATE
        int score = admission.calculateRiskScore(40.0, 22);
        assertAll(
                () -> assertEquals(50, score),
                () -> assertEquals("MODERATE", admission.getAdmissionPriority(score))
        );
    }
}
