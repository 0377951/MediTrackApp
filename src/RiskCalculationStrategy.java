/**
 * Strategy interface — different implementations encode different clinical
 * guidelines for translating temperature and age into a risk score.
 */
public interface RiskCalculationStrategy {
    int calculateRisk(double temperatureC, int age);
}
