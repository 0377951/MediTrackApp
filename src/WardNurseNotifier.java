/**
 * Observer that prints the ward assignment for the admitted patient.
 * Risk-tier driven so high-risk patients route to ICU automatically.
 */
public class WardNurseNotifier implements AdmissionObserver {
    @Override
    public void onPatientAdmitted(String patientName, int riskScore) {
        String ward = riskScore >= 70 ? "ICU"
                    : riskScore >= 30 ? "General Ward"
                                      : "Outpatient";
        System.out.println("[Ward Nurse] Assigning " + patientName
            + " to " + ward + " (risk " + riskScore + ")");
    }
}
