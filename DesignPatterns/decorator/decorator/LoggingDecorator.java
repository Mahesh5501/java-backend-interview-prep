package DesignPatterns.decorator.decorator;

import DesignPatterns.decorator.payment.PaymentProcessor;

import java.math.BigDecimal;

public class LoggingDecorator extends PaymentDecorator {

    public LoggingDecorator(PaymentProcessor paymentProcessor) {
        super(paymentProcessor);
    }

    @Override
    public void processPayment(BigDecimal amount) {

        // Additional behavior before the original operation.
        System.out.println("LOG: Payment processing started.");

        // Execute the wrapped processor.
        super.processPayment(amount);

        // Additional behavior after the original operation.
        System.out.println("LOG: Payment processing finished.");
    }
}
