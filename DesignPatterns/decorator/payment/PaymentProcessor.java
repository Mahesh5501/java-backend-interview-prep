package DesignPatterns.decorator.payment;

import java.math.BigDecimal;

// Every payment processor and decorator follows this contract.
public interface PaymentProcessor {

    void processPayment(BigDecimal amount);
}
