/**
 * Task 3 CHALLENGE — Observer + Strategy together. The Hospital is wired
 * with a ConservativeRiskStrategy at construction; admitPatient(name, temp,
 * age) computes the score via the strategy and then notifies all three
 * observers (doctor, billing, audit).
 */
public class CombinedDemo {
    public static void main(String[] args) {
        Hospital hospital = new Hospital(
            "MediTrack South",
            new ConservativeRiskStrategy()
        );

        hospital.addObserver(new DoctorNotifier("Dr. Lim"));
        hospital.addObserver(new BillingNotifier());
        hospital.addObserver(new AuditNotifier());

        System.out.println("--- Conservative strategy ---");
        hospital.admitPatient("Maria Santos", 38.7, 62);
        System.out.println();
        hospital.admitPatient("Ahmad Razali", 36.5, 25);

        // Swap strategy mid-run to show observers and algorithm vary independently.
        System.out.println();
        System.out.println("--- Switched to Pediatric strategy ---");
        hospital.setRiskStrategy(new PediatricRiskStrategy());
        hospital.admitPatient("Little Tomas", 38.5, 8);
    }
}
