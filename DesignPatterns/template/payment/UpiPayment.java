package DesignPatterns.template.payment;

import java.math.BigDecimal;

public class UpiPayment extends PaymentTemplate {

    @Override
    protected void authenticatePayment() {

        // UPI-specific authentication.
        System.out.println("Authenticating using UPI PIN...");
    }

    @Override
    protected void executePayment(BigDecimal amount) {

        // UPI-specific payment execution.
        System.out.println(
                "Processing UPI payment: INR " + amount
        );
    }
}
