import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PatientAdmissionTest {

    private PatientAdmission admission;

    @BeforeEach
    void setUp() {
        admission = new PatientAdmission();
    }

    // ----------------------------------------------------------------------
    // Task 2 BEGINNER: calculateRiskScore
    // ----------------------------------------------------------------------

    @Test
    void calculateRiskScore_highTempElderlyPatient_returnsHighScore() {
        int score = admission.calculateRiskScore(40.0, 70);
        assertEquals(80, score);
    }

    @Test
    void calculateRiskScore_normalTempYoungPatient_returnsZero() {
        int score = admission.calculateRiskScore(36.5, 25);
        assertEquals(0, score);
    }

    @Test
    void calculateRiskScore_elevatedTempMiddleAge_returnsMediumScore() {
        int score = admission.calculateRiskScore(38.0, 55);
        assertEquals(40, score);
    }

    // ----------------------------------------------------------------------
    // Task 2 BEGINNER: getAdmissionPriority
    // ----------------------------------------------------------------------

    @Test
    void getAdmissionPriority_scoreAbove70_returnsUrgent() {
        assertEquals("URGENT", admission.getAdmissionPriority(80));
    }

    @Test
    void getAdmissionPriority_scoreBetween30And70_returnsModerate() {
        assertEquals("MODERATE", admission.getAdmissionPriority(40));
    }

    @Test
    void getAdmissionPriority_scoreBelow30_returnsRoutine() {
        assertEquals("ROUTINE", admission.getAdmissionPriority(10));
    }

    // ----------------------------------------------------------------------
    // Task 2 BEGINNER: isValidPatientName
    // ----------------------------------------------------------------------

    @Test
    void isValidPatientName_validName_returnsTrue() {
        assertTrue(admission.isValidPatientName("Maria Santos"));
    }

    @Test
    void isValidPatientName_nullName_returnsFalse() {
        assertFalse(admission.isValidPatientName(null));
    }

    @Test
    void isValidPatientName_emptyString_returnsFalse() {
        assertFalse(admission.isValidPatientName(""));
    }

    // ----------------------------------------------------------------------
    // Task 2 INTERMEDIATE: boundary tests
    // ----------------------------------------------------------------------

    @Test
    void calculateRiskScore_temperatureExactly39_5_addsFiftyToScore() {
        // 39.5 is the boundary into the high-temp band; young patient -> 50 + 0
        int score = admission.calculateRiskScore(39.5, 30);
        assertEquals(50, score);
    }

    @Test
    void calculateRiskScore_ageExactly65_addsThirtyToScore() {
        // 65 is the boundary into the elderly band; normal temp -> 0 + 30
        int score = admission.calculateRiskScore(36.5, 65);
        assertEquals(30, score);
    }

    @Test
    void isValidPatientName_exactly100Characters_returnsTrue() {
        String name = "A".repeat(100);
        assertTrue(admission.isValidPatientName(name));
    }

    @Test
    void isValidPatientName_101Characters_returnsFalse() {
        String name = "A".repeat(101);
        assertFalse(admission.isValidPatientName(name));
    }

    // ----------------------------------------------------------------------
    // Task 2 CHALLENGE (TDD): isEligibleForPriorityCare
    // RED -> GREEN -> REFACTOR cycle. Four cases cover both branches and the
    // boundary at age 65.
    // ----------------------------------------------------------------------

    @Test
    void isEligibleForPriorityCare_youngHealthy_returnsFalse() {
        assertFalse(admission.isEligibleForPriorityCare(30, false));
    }

    @Test
    void isEligibleForPriorityCare_youngWithChronicCondition_returnsTrue() {
        assertTrue(admission.isEligibleForPriorityCare(30, true));
    }

    @Test
    void isEligibleForPriorityCare_elderlyHealthy_returnsTrue() {
        assertTrue(admission.isEligibleForPriorityCare(70, false));
    }

    @Test
    void isEligibleForPriorityCare_ageExactly65_returnsTrue() {
        assertTrue(admission.isEligibleForPriorityCare(65, false));
    }

    @Test
    void isEligibleForPriorityCare_elderlyWithChronicCondition_returnsTrue() {
        assertTrue(admission.isEligibleForPriorityCare(80, true));
    }

    // ----------------------------------------------------------------------
    // Task 3 BEGINNER: exception tests
    // ----------------------------------------------------------------------

    @Test
    void calculateRiskScore_temperatureBelow30_throwsIllegalArgument() {
        assertThrows(IllegalArgumentException.class,
                () -> admission.calculateRiskScore(20.0, 40));
    }

    @Test
    void calculateRiskScore_temperatureAbove45_throwsIllegalArgument() {
        assertThrows(IllegalArgumentException.class,
                () -> admission.calculateRiskScore(46.0, 40));
    }

    // ----------------------------------------------------------------------
    // Task 3 BEGINNER: parameterized test
    // ----------------------------------------------------------------------

    @ParameterizedTest
    @CsvSource({
            "40.0, 70, 80",  // high temp + elderly      = 50 + 30
            "38.0, 55, 40",  // moderate temp + middle   = 25 + 15
            "36.5, 25,  0",  // normal temp + young      =  0 +  0
            "39.5, 30, 50",  // boundary high + young    = 50 +  0
    })
    void calculateRiskScore_variousInputs_returnsCorrectScore(
            double temp, int age, int expectedScore) {
        assertEquals(expectedScore, admission.calculateRiskScore(temp, age));
    }

    // ----------------------------------------------------------------------
    // Task 3 CHALLENGE: formatPatientReport
    //
    // Acceptance criteria (also reproduced in SUBMISSION_NOTES.md):
    //   AC1: Returns a non-null string for valid inputs.
    //   AC2: Output uses the format "Patient: <name> | Risk: <score> | Priority: <priority>".
    //   AC3: Leading and trailing whitespace in name is trimmed.
    //   AC4: Risk score appears in output as an integer (no decimal).
    //   AC5: Priority must be one of URGENT / MODERATE / ROUTINE; any other
    //        value causes IllegalArgumentException.
    //
    // Plus three edge-case tests: null name, negative score, unknown priority.
    // ----------------------------------------------------------------------

    @Test
    void formatPatientReport_validInputs_returnsNonNullString_AC1() {
        String report = admission.formatPatientReport("Maria Santos", 80, "URGENT");
        assertTrue(report != null && !report.isEmpty());
    }

    @Test
    void formatPatientReport_validInputs_usesExpectedFormat_AC2() {
        String report = admission.formatPatientReport("Maria Santos", 80, "URGENT");
        assertEquals("Patient: Maria Santos | Risk: 80 | Priority: URGENT", report);
    }

    @Test
    void formatPatientReport_nameWithSurroundingWhitespace_isTrimmed_AC3() {
        String report = admission.formatPatientReport("  Maria Santos  ", 80, "URGENT");
        assertEquals("Patient: Maria Santos | Risk: 80 | Priority: URGENT", report);
    }

    @Test
    void formatPatientReport_riskScore_appearsAsInteger_AC4() {
        String report = admission.formatPatientReport("Maria Santos", 40, "MODERATE");
        assertTrue(report.contains("Risk: 40"));
        assertFalse(report.contains("40.0"));
    }

    @Test
    void formatPatientReport_unknownPriority_throwsIllegalArgument_AC5() {
        assertThrows(IllegalArgumentException.class,
                () -> admission.formatPatientReport("Maria Santos", 80, "HIGH"));
    }

    // --- Edge-case tests ---

    @Test
    void formatPatientReport_nullName_throwsIllegalArgument() {
        assertThrows(IllegalArgumentException.class,
                () -> admission.formatPatientReport(null, 80, "URGENT"));
    }

    @Test
    void formatPatientReport_negativeRiskScore_throwsIllegalArgument() {
        assertThrows(IllegalArgumentException.class,
                () -> admission.formatPatientReport("Maria Santos", -1, "URGENT"));
    }

    @Test
    void formatPatientReport_emptyPriority_throwsIllegalArgument() {
        assertThrows(IllegalArgumentException.class,
                () -> admission.formatPatientReport("Maria Santos", 80, ""));
    }
}
