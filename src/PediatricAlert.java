public class PediatricAlert implements AlertConfig {
    @Override public double getThreshold()    { return 38.5; }
    @Override public String getAlertMessage() { return "Paediatric alert — contact on-call paediatrician"; }
}
