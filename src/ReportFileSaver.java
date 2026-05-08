/**
 * Single Responsibility: persist a report to a file destination.
 */
public class ReportFileSaver {
    public void save(String report, String filename) {
        System.out.println("Saving '" + report + "' to " + filename);
    }
}
