/**
 * Open/Closed Principle abstraction for sending a report to a recipient.
 * New notification channels (SMS, push, etc.) implement this interface
 * without modifying existing notifier classes.
 */
public interface Notifier {
    void send(String report, String recipient);
}
