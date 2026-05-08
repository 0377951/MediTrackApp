import java.util.ArrayList;
import java.util.List;

/**
 * Observer pattern — Subject. Maintains a list of subscribers and fans out
 * admission events to them. New notification types subscribe at registration
 * time without any change to this class (Open/Closed Principle).
 */
public class Hospital implements AdmissionSubject {

    private final List<AdmissionObserver> observers = new ArrayList<>();
    private final String hospitalName;

    public Hospital(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    @Override
    public void addObserver(AdmissionObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(AdmissionObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String patientName, int riskScore) {
        for (AdmissionObserver observer : observers) {
            observer.onPatientAdmitted(patientName, riskScore);
        }
    }

    /**
     * Admit a patient and fan the event out to every registered observer.
     */
    public void admitPatient(String patientName, int riskScore) {
        System.out.println("[" + hospitalName + "] Admitting: " + patientName);
        notifyObservers(patientName, riskScore);
    }
}
