/**
 * Single Responsibility: format a patient report line.
 */
public class PatientReportFormatter {
    public String format(String name, int riskScore) {
        return "Patient: " + name + " | Risk: " + riskScore;
    }
}
