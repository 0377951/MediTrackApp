/**
 * MediTrack patient admission logic.
 *
 * Encapsulates risk scoring, priority assignment, and report formatting for
 * the Sprint 1 admission module. All inputs that cross a system boundary
 * are validated; invalid inputs throw IllegalArgumentException.
 */
public class PatientAdmission {

    /**
     * Calculates a risk score (0-100) from temperature and age.
     * Higher score = higher risk.
     *
     * @throws IllegalArgumentException if temperatureC is outside [30.0, 45.0]
     */
    public int calculateRiskScore(double temperatureC, int age) {
        if (temperatureC < 30.0 || temperatureC > 45.0) {
            throw new IllegalArgumentException(
                "Temperature out of valid range: " + temperatureC);
        }
        int score = 0;
        if (temperatureC >= 39.5) score += 50;
        else if (temperatureC >= 37.5) score += 25;
        if (age >= 65) score += 30;
        else if (age >= 50) score += 15;
        return score;
    }

    /**
     * Returns admission priority: "URGENT", "MODERATE", or "ROUTINE".
     */
    public String getAdmissionPriority(int riskScore) {
        if (riskScore >= 70) return "URGENT";
        if (riskScore >= 30) return "MODERATE";
        return "ROUTINE";
    }

    /**
     * Checks whether a patient name is valid (non-null, non-empty, <= 100 chars).
     */
    public boolean isValidPatientName(String name) {
        return name != null && !name.trim().isEmpty() && name.length() <= 100;
    }

    /**
     * A patient qualifies for priority care if they are 65+ OR have a chronic condition.
     */
    public boolean isEligibleForPriorityCare(int age, boolean hasChronicCondition) {
        return age >= 65 || hasChronicCondition;
    }

    /**
     * Formats a one-line admission report.
     * Format: "Patient: <name> | Risk: <score> | Priority: <priority>"
     *
     * @throws IllegalArgumentException if name is null, riskScore < 0,
     *         or priority is not one of URGENT / MODERATE / ROUTINE.
     */
    public String formatPatientReport(String name, int riskScore, String priority) {
        if (name == null) {
            throw new IllegalArgumentException("Patient name cannot be null");
        }
        if (riskScore < 0) {
            throw new IllegalArgumentException("Risk score cannot be negative: " + riskScore);
        }
        if (!"URGENT".equals(priority)
                && !"MODERATE".equals(priority)
                && !"ROUTINE".equals(priority)) {
            throw new IllegalArgumentException("Unknown priority: " + priority);
        }
        return "Patient: " + name.trim()
                + " | Risk: " + riskScore
                + " | Priority: " + priority;
    }
}
