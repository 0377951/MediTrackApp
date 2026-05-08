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
    private RiskCalculationStrategy riskStrategy;

    public Hospital(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    /**
     * Task 3 challenge — combine Observer + Strategy. Inject the risk
     * algorithm at construction so the hospital can compute the score
     * itself before fanning out to observers.
     */
    public Hospital(String hospitalName, RiskCalculationStrategy riskStrategy) {
        this.hospitalName = hospitalName;
        this.riskStrategy = riskStrategy;
    }

    public void setRiskStrategy(RiskCalculationStrategy riskStrategy) {
        this.riskStrategy = riskStrategy;
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

    /**
     * Task 3 challenge — admit a patient by computing the risk score with
     * the configured strategy first, then notifying observers. Combines the
     * Strategy and Observer patterns.
     *
     * @throws IllegalStateException if no strategy has been configured
     */
    public void admitPatient(String patientName, double temperatureC, int age) {
        if (riskStrategy == null) {
            throw new IllegalStateException(
                "No RiskCalculationStrategy configured for hospital: " + hospitalName);
        }
        int score = riskStrategy.calculateRisk(temperatureC, age);
        admitPatient(patientName, score);
    }
}
