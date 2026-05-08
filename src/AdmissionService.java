/**
 * Refactored slice of HospitalSystem — admissions only (SRP).
 * Depends on a Notifier abstraction, not a concrete channel (DIP).
 */
public class AdmissionService {
    private final Notifier notifier;

    public AdmissionService(Notifier notifier) {
        this.notifier = notifier;
    }

    public void admitPatient(String name) {
        System.out.println("Admitted: " + name);
        notifier.send("Admitted: " + name, "ward-desk@meditrack.com");
    }
}
