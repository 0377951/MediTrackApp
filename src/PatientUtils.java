public class PatientUtils {

    public static String assessRisk(double tempC) {
        if (tempC >= 39.5) return "HIGH";
        if (tempC >= 37.5) return "MODERATE";
        return "LOW";
    }

    public static double averageReading(double r1, double r2, double r3) {
        return (r1 + r2 + r3) / 3.0;
    }

    public static boolean isElderly(int age) {
        return age >= 65;
    }

    public static void main(String[] args) {
        double temp = 38.9;
        System.out.println("Risk Level:      " + assessRisk(temp));
        System.out.println("Average Reading: " + averageReading(37.1, 38.9, 37.5));
        System.out.println("Is Elderly:      " + isElderly(70));
    }
}
