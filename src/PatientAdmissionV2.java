/**
 * Strategy-pattern version of PatientAdmission. The risk algorithm is
 * injected at construction time and can be swapped at runtime without
 * editing this class — this is the Open/Closed Principle in action.
 */
public class PatientAdmissionV2 {

    private RiskCalculationStrategy strategy;

    public PatientAdmissionV2(RiskCalculationStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(RiskCalculationStrategy strategy) {
        this.strategy = strategy;
    }

    public int calculateRiskScore(double temperatureC, int age) {
        return strategy.calculateRisk(temperatureC, age);
    }
}
