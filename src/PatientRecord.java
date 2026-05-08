public class PatientRecord {
    public static void main(String[] args) {

        // Patient data - use meaningful names (Clean Code, Ch. 2)
        int     patientAge   = 45;
        double  bodyTempC    = 37.2;
        boolean isAdmitted   = true;
        char    bloodType    = 'O';
        String  patientName  = "Maria Santos";

        System.out.println("=== Patient Record ===");
        System.out.println("Name:        " + patientName);
        System.out.println("Age:         " + patientAge);
        System.out.println("Temperature: " + bodyTempC + "°C");
        System.out.println("Blood Type:  " + bloodType);
        System.out.println("Admitted:    " + isAdmitted);
    }
}
