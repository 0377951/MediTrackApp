/**
 * Observer that opens a billing account when a patient is admitted.
 */
public class BillingNotifier implements AdmissionObserver {
    @Override
    public void onPatientAdmitted(String patientName, int riskScore) {
        System.out.println("[Billing] Opening account for: " + patientName);
    }
}
