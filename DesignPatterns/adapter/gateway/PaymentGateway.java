package DesignPatterns.adapter.gateway;

import java.math.BigDecimal;

// This is the common interface our application expects.
// Every payment gateway adapter must follow this contract.
public interface PaymentGateway {

    void processPayment(BigDecimal amount);
}
