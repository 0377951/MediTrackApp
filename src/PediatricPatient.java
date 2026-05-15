public class PediatricPatient extends Patient {
    public PediatricPatient(String name, int age, double temp) { super(name, age, temp); }
    @Override public double getAlertTemperatureThreshold() { return 38.5; } // more sensitive
    @Override public String getPatientType() { return "Pediatric Patient (age " + age + ")"; }
    @Override public String getTreatmentProtocol() {
        return "Paediatric ward protocol — reduced medication dosages";
    }
}
