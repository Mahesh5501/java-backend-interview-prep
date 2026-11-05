package DesignPatterns.decorator.payment;

import java.math.BigDecimal;

public class BasicPaymentProcessor implements PaymentProcessor {

    @Override
    public void processPayment(BigDecimal amount) {

        // This class contains only the actual payment processing logic.
        System.out.println(
                "Processing payment of INR " + amount
        );
    }
}
