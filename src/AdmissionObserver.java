/**
 * Observer pattern — notification handlers implement this interface so the
 * Hospital does not need to know what type of handler it is calling.
 */
public interface AdmissionObserver {
    void onPatientAdmitted(String patientName, int riskScore);
}
