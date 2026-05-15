public class GeneralPatient extends Patient {
    public GeneralPatient(String name, int age, double temp) { super(name, age, temp); }
    @Override public double getAlertTemperatureThreshold() { return 39.5; }
    @Override public String getPatientType() { return "General Patient"; }
    @Override public String getTreatmentProtocol() {
        return "Standard observation and medication";
    }
}
