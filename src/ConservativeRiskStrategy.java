/**
 * Conservative risk thresholds — flags risk earlier than the standard
 * guideline. Used in regions with stricter clinical policy.
 *  - High temp band: >= 38.5 °C  -> +50  (lower than standard)
 *  - Mid  temp band: >= 37.0 °C  -> +25
 *  - Elderly band  : age >= 60   -> +30  (lower than standard)
 */
public class ConservativeRiskStrategy implements RiskCalculationStrategy {
    @Override
    public int calculateRisk(double temperatureC, int age) {
        int score = 0;
        if (temperatureC >= 38.5) score += 50;
        else if (temperatureC >= 37.0) score += 25;
        if (age >= 60) score += 30;
        return score;
    }
}
