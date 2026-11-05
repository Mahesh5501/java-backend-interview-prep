package DesignPatterns.decorator.decorator;

import DesignPatterns.decorator.payment.PaymentProcessor;

import java.math.BigDecimal;

// Decorator implements the same interface as the original component.
// It also holds another PaymentProcessor object.
// This allows us to wrap either the basic processor or another decorator.
public abstract class PaymentDecorator implements PaymentProcessor {

    protected final PaymentProcessor paymentProcessor;

    protected PaymentDecorator(PaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }

    @Override
    public void processPayment(BigDecimal amount) {

        // By default, delegate the operation to the wrapped object.
        paymentProcessor.processPayment(amount);
    }
}
