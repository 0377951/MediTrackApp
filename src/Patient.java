public class Patient {

    String  name;
    int     age;
    double  temperatureC;
    String  bloodType;
    boolean isAdmitted;

    public Patient(String name, int age, double temperatureC, String bloodType, boolean isAdmitted) {
        this.name         = name;
        this.age          = age;
        this.temperatureC = temperatureC;
        this.bloodType    = bloodType;
        this.isAdmitted   = isAdmitted;
    }

    public String assessRisk() {
        if (temperatureC >= 39.5) return "HIGH";
        if (temperatureC >= 37.5) return "MODERATE";
        return "LOW";
    }

    public void printSummary() {
        System.out.println("--- Patient Summary ---");
        System.out.println("Name:     " + name);
        System.out.println("Age:      " + age);
        System.out.println("Temp:     " + temperatureC + "°C");
        System.out.println("Blood:    " + bloodType);
        System.out.println("Risk:     " + assessRisk());
        System.out.println("Admitted: " + isAdmitted);
    }

    public static void main(String[] args) {
        Patient p1 = new Patient("Maria Santos", 45, 38.9, "O+", true);
        Patient p2 = new Patient("Ahmad Razali", 72, 37.1, "A-", false);
        Patient p3 = new Patient("Ethan Smith",  28, 36.6, "B+", false);

        p1.printSummary();
        p2.printSummary();
        p3.printSummary();
    }
}
