import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PatientV2Test {

    @Test
    void needsAlert_elderlyAlertConfig_alertsAtLowerThreshold() {
        // ElderlyAlert threshold is 38.0 — 38.1°C must trigger an alert.
        PatientV2 patient = new PatientV2(
            "Ahmad Razali", 72, 38.1,
            new ElderlyAlert(), new StandardProtocol());

        assertTrue(patient.needsAlert(),
            "ElderlyAlert (threshold 38.0) must trigger at 38.1°C");
    }

    @Test
    void upgradeAlertConfig_newConfigApplied_thresholdChanges() {
        PatientV2 patient = new PatientV2(
            "Ahmad Razali", 72, 38.2,
            new ElderlyAlert(), new DiabeticProtocol());

        // Before upgrade — Elderly threshold 38.0, so 38.2°C alerts.
        assertEquals(38.0, patient.getAlertConfig().getThreshold(),
            "Initial threshold should be Elderly (38.0)");
        assertTrue(patient.needsAlert(),
            "38.2°C should alert under ElderlyAlert");

        // Upgrade to PediatricAlert (threshold 38.5).
        patient.upgradeAlertConfig(new PediatricAlert());

        assertEquals(38.5, patient.getAlertConfig().getThreshold(),
            "After upgrade, threshold should be Paediatric (38.5)");
        assertFalse(patient.needsAlert(),
            "38.2°C should no longer alert under PediatricAlert (threshold 38.5)");
    }
}
