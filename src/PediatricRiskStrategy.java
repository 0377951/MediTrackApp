/**
 * Pediatric risk strategy — used in children's wards. Demonstrates OCP:
 * adding this strategy required NO change to PatientAdmissionV2.
 *  - Pediatric band: age < 12        -> +20
 *  - Pediatric fever: temp >= 38.0   -> +40
 */
public class PediatricRiskStrategy implements RiskCalculationStrategy {
    @Override
    public int calculateRisk(double temperatureC, int age) {
        int score = 0;
        if (age < 12) score += 20;
        if (temperatureC >= 38.0) score += 40;
        return score;
    }
}
