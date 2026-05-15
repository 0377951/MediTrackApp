import java.util.List;

public class PatientWardDemo {
    public static void main(String[] args) {
        List<Patient> ward = List.of(
            new GeneralPatient("Maria Santos",  45, 39.0),
            new PediatricPatient("Leo Tan",       8, 38.7),
            new ElderlyPatient("Ahmad Razali",  72, 38.2)
        );

        for (Patient p : ward) {
            p.printSummary();
        }

        // Count alerts
        long alertCount = ward.stream().filter(Patient::needsAlert).count();
        System.out.println("Patients requiring alert: " + alertCount);
    }
}
