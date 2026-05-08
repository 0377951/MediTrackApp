/**
 * Task 1 demo: drives the four single-responsibility classes through the
 * Notifier interface so Email and SMS are interchangeable without touching
 * any other class (Open/Closed Principle).
 */
public class PatientReportDemo {
    public static void main(String[] args) {
        PatientReportFormatter formatter = new PatientReportFormatter();
        ReportFileSaver saver = new ReportFileSaver();
        AuditLogger logger = new AuditLogger();

        String report = formatter.format("Maria Santos", 80);
        saver.save(report, "report_001.txt");

        // OCP: same reference type, swappable implementations.
        Notifier notifier = new EmailNotifier();
        notifier.send(report, "doctor@meditrack.com");

        notifier = new SmsNotifier();
        notifier.send(report, "+60-12-345-6789");

        logger.log("Report generated for Maria Santos");
    }
}
