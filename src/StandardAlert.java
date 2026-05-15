public class StandardAlert implements AlertConfig {
    @Override public double getThreshold()    { return 39.5; }
    @Override public String getAlertMessage() { return "Standard fever alert"; }
}
