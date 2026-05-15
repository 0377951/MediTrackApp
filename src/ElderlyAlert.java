public class ElderlyAlert implements AlertConfig {
    @Override public double getThreshold()    { return 38.0; }
    @Override public String getAlertMessage() { return "Elderly alert — priority escalation"; }
}
