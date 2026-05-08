/**
 * Single Responsibility: send a report via SMS.
 * Added without modifying {@link EmailNotifier} — demonstrates OCP.
 */
public class SmsNotifier implements Notifier {
    @Override
    public void send(String report, String recipient) {
        System.out.println("SMS to " + recipient + ": '" + report + "'");
    }
}
