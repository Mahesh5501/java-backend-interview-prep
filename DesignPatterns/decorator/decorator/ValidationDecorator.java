package DesignPatterns.decorator.decorator;

import DesignPatterns.decorator.payment.PaymentProcessor;

import java.math.BigDecimal;

public class ValidationDecorator extends PaymentDecorator {

    public ValidationDecorator(PaymentProcessor paymentProcessor) {
        super(paymentProcessor);
    }

    @Override
    public void processPayment(BigDecimal amount) {

        // Validate before allowing the wrapped processor to execute.
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                    "Payment amount must be greater than zero"
            );
        }

        System.out.println("VALIDATION: Payment amount is valid.");

        // Continue to the next wrapped object.
        super.processPayment(amount);
    }
}
