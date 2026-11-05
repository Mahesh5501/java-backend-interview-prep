package DesignPatterns.proxy.payment;

import java.math.BigDecimal;

// Both the real service and proxy follow the same contract.
// This allows the client to use either without knowing the difference.
public interface PaymentProcessor {

    void processPayment(String userRole, BigDecimal amount);
}