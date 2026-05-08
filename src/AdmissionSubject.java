/**
 * Observer pattern — the publishable side. Hospital implements this so any
 * AdmissionObserver can subscribe to admission events.
 */
public interface AdmissionSubject {
    void addObserver(AdmissionObserver observer);
    void removeObserver(AdmissionObserver observer);
    void notifyObservers(String patientName, int riskScore);
}
