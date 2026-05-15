// Alert configuration — defines when to raise an alert
public interface AlertConfig {
    double getThreshold();
    String getAlertMessage();
}
