/**
 * Task 1 CHALLENGE — VIOLATION example. Kept verbatim from the lab brief
 * for comparison with the refactored version (see HospitalSystemRefactored*
 * classes and docs/Task1_SOLID_violations.md).
 *
 * Violations: SRP (six unrelated responsibilities), OCP (no abstraction —
 * adding a new notification or invoice type forces edits here), DIP (depends
 * on concrete I/O directly rather than abstractions), and ISP (clients are
 * forced to depend on methods they do not use).
 */
public class HospitalSystem {
    public void admitPatient(String name) { /* ... */ }
    public void generateInvoice(String name) { /* ... */ }
    public void sendSMS(String message) { /* ... */ }
    public void connectToDatabase() { /* ... */ }
    public void printReport(String report) { /* ... */ }
    public double calculateInsurance(String plan, double amount) { return 0.0; }
}
