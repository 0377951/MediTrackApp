/**
 * Task 2 demo. Walks through:
 *   1. Three baseline observers receiving every admission.
 *   2. Adding a ward-nurse observer, admitting, then removing the nurse and
 *      admitting again — the nurse must NOT be notified the second time.
 *   3. The urgent-only doctor filter ignoring a routine admission.
 */
public class ObserverDemo {
    public static void main(String[] args) {
        Hospital hospital = new Hospital("MediTrack Central");

        DoctorNotifier doctor = new DoctorNotifier("Dr. Lim");
        BillingNotifier billing = new BillingNotifier();
        AuditNotifier audit = new AuditNotifier();

        hospital.addObserver(doctor);
        hospital.addObserver(billing);
        hospital.addObserver(audit);

        System.out.println("--- Baseline: 3 observers ---");
        hospital.admitPatient("Maria Santos", 80);
        System.out.println();
        hospital.admitPatient("Ahmad Razali", 25);

        System.out.println();
        System.out.println("--- Dynamic add/remove: ward nurse ---");
        WardNurseNotifier nurse = new WardNurseNotifier();
        hospital.addObserver(nurse);
        hospital.admitPatient("Lina Chen", 75);    // nurse IS notified

        hospital.removeObserver(nurse);
        System.out.println("(nurse removed)");
        hospital.admitPatient("Tomas Reyes", 40);  // nurse NOT notified

        System.out.println();
        System.out.println("--- Urgent-only doctor filter ---");
        Hospital filtered = new Hospital("MediTrack East");
        filtered.addObserver(new UrgentOnlyDoctorNotifier("Dr. Patel"));
        filtered.addObserver(new BillingNotifier());
        filtered.admitPatient("High-Risk Patient", 90);  // doctor sees this
        filtered.admitPatient("Routine Patient", 25);    // doctor stays silent
    }
}
