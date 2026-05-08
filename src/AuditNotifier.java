/**
 * Observer that writes an audit-log entry whenever a patient is admitted.
 */
public class AuditNotifier implements AdmissionObserver {
    @Override
    public void onPatientAdmitted(String patientName, int riskScore) {
        System.out.println("[Audit Log] " + patientName + " admitted at "
            + java.time.LocalTime.now());
    }
}
