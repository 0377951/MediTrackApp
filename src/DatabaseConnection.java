/**
 * Refactored slice of HospitalSystem — persistence concern only (SRP).
 * In production this would be an interface with concrete drivers; here
 * the stub is enough to demonstrate separation.
 */
public class DatabaseConnection {
    public void connect() {
        System.out.println("Database connected.");
    }
}
