public abstract class Patient {

    protected String name;
    protected int    age;
    protected double temperatureC;

    public Patient(String name, int age, double temperatureC) {
        this.name = name; this.age = age; this.temperatureC = temperatureC;
    }

    // Abstract: each patient type defines its own alert threshold
    public abstract double getAlertTemperatureThreshold();

    // Concrete: shared by all patient types
    public boolean needsAlert() {
        return this.temperatureC >= getAlertTemperatureThreshold();
    }

    public void printSummary() {
        System.out.println("=== " + getPatientType() + " ===");
        System.out.println("Name: " + name + " | Age: " + age
            + " | Temp: " + temperatureC + " | Alert: " + needsAlert());
        System.out.println("Treatment: " + getTreatmentProtocol());
    }

    public abstract String getPatientType();

    public abstract String getTreatmentProtocol();
}
