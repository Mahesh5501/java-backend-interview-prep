package DesignPatterns.template.payment;

import java.math.BigDecimal;

public class CardPayment extends PaymentTemplate {

    @Override
    protected void authenticatePayment() {

        // Card-specific authentication.
        System.out.println("Authenticating using OTP...");
    }

    @Override
    protected void executePayment(BigDecimal amount) {

        // Card-specific payment execution.
        System.out.println(
                "Processing Card payment: INR " + amount
        );
    }
}
