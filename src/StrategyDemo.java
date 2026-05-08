/**
 * Task 3 demo — same patient run through two strategies, then a runtime
 * strategy swap on a single PatientAdmissionV2 instance.
 */
public class StrategyDemo {
    public static void main(String[] args) {
        double temp = 38.7;
        int age = 62;

        PatientAdmissionV2 standard = new PatientAdmissionV2(new StandardRiskStrategy());
        PatientAdmissionV2 conservative = new PatientAdmissionV2(new ConservativeRiskStrategy());

        System.out.println("Patient: temp=" + temp + " °C, age=" + age);
        System.out.println("Standard risk:     " + standard.calculateRiskScore(temp, age));
        System.out.println("Conservative risk: " + conservative.calculateRiskScore(temp, age));

        // Swap at runtime — same instance, new behaviour, no class edits.
        standard.setStrategy(new ConservativeRiskStrategy());
        System.out.println("After swap:        " + standard.calculateRiskScore(temp, age));

        // Demonstrate Pediatric strategy without modifying PatientAdmissionV2.
        PatientAdmissionV2 pediatric = new PatientAdmissionV2(new PediatricRiskStrategy());
        System.out.println("Pediatric (8yo, 38.5 °C): "
            + pediatric.calculateRiskScore(38.5, 8));
    }
}
