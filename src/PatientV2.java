public class PatientV2 {

    private String            name;
    private int               age;
    private double            temperatureC;
    private AlertConfig       alertConfig;   // HAS-A
    private TreatmentProtocol protocol;      // HAS-A

    public PatientV2(String name, int age, double temperatureC,
                     AlertConfig alertConfig, TreatmentProtocol protocol) {
        this.name          = name;
        this.age           = age;
        this.temperatureC  = temperatureC;
        this.alertConfig   = alertConfig;
        this.protocol      = protocol;
    }

    // Delegation: PatientV2 delegates to alertConfig
    public boolean needsAlert() {
        return this.temperatureC >= alertConfig.getThreshold();
    }

    public void printSummary() {
        System.out.println("=== Patient: " + name + " (Age " + age + ") ===");
        System.out.println("  Temp: " + temperatureC);
        System.out.println("  Protocol: " + protocol.getProtocolDescription());
        if (needsAlert()) System.out.println("  ALERT: " + alertConfig.getAlertMessage());
    }

    // Runtime swap — change alert config without rebuilding the patient
    public void upgradeAlertConfig(AlertConfig newConfig) {
        this.alertConfig = newConfig;
    }

    public AlertConfig getAlertConfig() {
        return alertConfig;
    }
}
