/**
 * Single Responsibility: write audit log entries.
 */
public class AuditLogger {
    public void log(String action) {
        System.out.println("[AUDIT] " + action);
    }
}
