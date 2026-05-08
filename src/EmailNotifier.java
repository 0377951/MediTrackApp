/**
 * Single Responsibility: send a report via email.
 * Implements {@link Notifier} so callers can swap channels without changing them.
 */
public class EmailNotifier implements Notifier {
    @Override
    public void send(String report, String recipient) {
        System.out.println("Emailing '" + report + "' to " + recipient);
    }
}
