import java.time.LocalDate;

public class HelloMediTrack {

    public static void main(String[] args) {
        // Sprint 0 verification - confirms environment is working
        System.out.println("MediTrack Patient System - v0.1");
        System.out.println("Developer: Hishaam");
        System.out.println("Student ID: 0377951");
        System.out.println("Status: Environment configured successfully.");

        LocalDate today = LocalDate.now();
        System.out.println("Welcome to MediTrack, Taylor. Today is " + today + ".");
    }
}
