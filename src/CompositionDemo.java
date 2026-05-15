public class CompositionDemo {
    public static void main(String[] args) {

        // General patient — standard alert + standard protocol
        PatientV2 general = new PatientV2(
            "Maria Santos", 45, 39.0,
            new StandardAlert(), new StandardProtocol());

        // Elderly + Diabetic patient — mix alert and protocol freely
        PatientV2 elderlyDiabetic = new PatientV2(
            "Ahmad Razali", 72, 38.2,
            new ElderlyAlert(), new DiabeticProtocol());

        // Paediatric patient
        PatientV2 child = new PatientV2(
            "Leo Tan", 8, 38.7,
            new PediatricAlert(), new StandardProtocol());

        general.printSummary();
        elderlyDiabetic.printSummary();
        child.printSummary();

        // Runtime swap — upgrade Ahmad's alert config to PediatricAlert
        System.out.println("\n--- Upgrading Ahmad's alert config to PediatricAlert ---");
        elderlyDiabetic.upgradeAlertConfig(new PediatricAlert());
        System.out.println("New threshold: " + elderlyDiabetic.getAlertConfig().getThreshold());
        elderlyDiabetic.printSummary();
    }
}
