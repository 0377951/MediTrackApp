/**
 * Refactored slice of HospitalSystem — invoices and insurance only (SRP).
 * Insurance calculation is delegated to an injected strategy so adding a
 * new insurance plan does not modify this class (OCP / DIP).
 */
public class BillingService {

    public interface InsurancePlan {
        double calculate(double amount);
    }

    public void generateInvoice(String name) {
        System.out.println("Invoice generated for: " + name);
    }

    public double calculateInsurance(InsurancePlan plan, double amount) {
        return plan.calculate(amount);
    }
}
