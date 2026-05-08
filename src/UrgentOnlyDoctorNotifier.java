/**
 * Observer wrapper that only forwards admissions whose risk score meets the
 * urgent threshold. The filter lives in the observer, not the subject, so
 * other observers (billing, audit) still see every admission.
 */
public class UrgentOnlyDoctorNotifier implements AdmissionObserver {

    private static final int URGENT_THRESHOLD = 70;

    private final String doctorName;

    public UrgentOnlyDoctorNotifier(String doctorName) {
        this.doctorName = doctorName;
    }

    @Override
    public void onPatientAdmitted(String patientName, int riskScore) {
        if (riskScore < URGENT_THRESHOLD) {
            return;
        }
        System.out.println("[URGENT — Doctor " + doctorName + "] " + patientName
            + " admitted with risk " + riskScore);
    }
}
