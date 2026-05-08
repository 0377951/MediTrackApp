public class PatientRoster {

    public static void main(String[] args) {
        Patient[] roster = {
            new Patient("Maria Santos",   45, 38.9, "O+", true),
            new Patient("Ahmad Razali",   72, 37.1, "A-", false),
            new Patient("Liu Wei",        31, 40.1, "B+", true),
            new Patient("Sara Okonkwo",   58, 36.8, "AB+", false),
            new Patient("James Murphy",   65, 39.6, "O-", true)
        };

        for (Patient patient : roster) {
            patient.printSummary();
        }

        Patient highestTemp = roster[0];
        for (Patient patient : roster) {
            if (patient.temperatureC > highestTemp.temperatureC) {
                highestTemp = patient;
            }
        }
        System.out.println("\nHighest temperature: " + highestTemp.name +
                           " (" + highestTemp.temperatureC + "°C)");

        int highRiskCount = 0;
        for (Patient patient : roster) {
            if (patient.assessRisk().equals("HIGH")) {
                highRiskCount++;
            }
        }
        System.out.println("HIGH risk patients: " + highRiskCount);
    }
}
