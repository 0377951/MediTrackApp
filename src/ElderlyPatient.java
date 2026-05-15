public class ElderlyPatient extends Patient {
    public ElderlyPatient(String name, int age, double temp) { super(name, age, temp); }
    @Override public double getAlertTemperatureThreshold() { return 38.0; } // most sensitive
    @Override public String getPatientType() { return "Elderly Patient (age " + age + ")"; }
    @Override public String getTreatmentProtocol() {
        return "Geriatric care protocol — fall risk assessment required";
    }
}
