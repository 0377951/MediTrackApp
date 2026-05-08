/**
 * Standard WHO-style risk thresholds.
 *  - High temp band: >= 39.5 °C  -> +50
 *  - Mid  temp band: >= 37.5 °C  -> +25
 *  - Elderly band  : age >= 65   -> +30
 */
public class StandardRiskStrategy implements RiskCalculationStrategy {
    @Override
    public int calculateRisk(double temperatureC, int age) {
        int score = 0;
        if (temperatureC >= 39.5) score += 50;
        else if (temperatureC >= 37.5) score += 25;
        if (age >= 65) score += 30;
        return score;
    }
}
