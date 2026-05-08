/**
 * Task 1 — SRP VIOLATION example (kept as study reference, NOT used in production).
 *
 * This class deliberately mixes four responsibilities: formatting, file I/O,
 * email notification, and audit logging. It exists so the refactor target is
 * visible alongside the refactored classes (PatientReportFormatter,
 * ReportFileSaver, EmailNotifier, AuditLogger).
 */
public class PatientReportManager {

    // Responsibility 1: Data formatting
    public String formatReport(String name, int riskScore) {
        return "Patient: " + name + " | Risk: " + riskScore;
    }

    // Responsibility 2: File saving
    public void saveToFile(String report, String filename) {
        System.out.println("Saving '" + report + "' to " + filename);
    }

    // Responsibility 3: Email sending
    public void sendByEmail(String report, String recipient) {
        System.out.println("Emailing '" + report + "' to " + recipient);
    }

    // Responsibility 4: Audit logging
    public void logAudit(String action) {
        System.out.println("[AUDIT] " + action);
    }
}
