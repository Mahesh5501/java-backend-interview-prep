package DesignPatterns.decorator.decorator;

import DesignPatterns.decorator.payment.PaymentProcessor;

import java.math.BigDecimal;

public class MetricsDecorator extends PaymentDecorator {

    public MetricsDecorator(PaymentProcessor paymentProcessor) {
        super(paymentProcessor);
    }

    @Override
    public void processPayment(BigDecimal amount) {

        // Additional behavior before processing.
        System.out.println("METRICS: Recording payment attempt.");

        // Execute the wrapped processor and decorators.
        super.processPayment(amount);

        // Additional behavior after processing.
        System.out.println("METRICS: Recording payment completion.");
    }
}
