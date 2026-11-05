package DesignPatterns.template.payment;

import java.math.BigDecimal;

// Abstract parent class defines the common payment workflow.
public abstract class PaymentTemplate {

    // Template method:
    // Defines the overall sequence of payment processing.
    // final prevents subclasses from changing this workflow.
    public final void processPayment(BigDecimal amount) {

        validatePayment(amount);

        authenticatePayment();

        executePayment(amount);

        generateReceipt();

        System.out.println("Payment workflow completed.");
    }

    // Common implementation shared by all payment types.
    private void validatePayment(BigDecimal amount) {

        if (amount == null ||
                amount.compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                    "Invalid payment amount"
            );
        }

        System.out.println("Validating payment...");
    }

    // Subclasses must provide their own authentication.
    protected abstract void authenticatePayment();

    // Subclasses must provide their own payment execution.
    protected abstract void executePayment(BigDecimal amount);

    // Common implementation shared by all payment types.
    private void generateReceipt() {

        System.out.println("Generating payment receipt...");
    }
}
