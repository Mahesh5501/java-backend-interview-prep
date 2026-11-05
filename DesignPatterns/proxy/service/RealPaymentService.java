package DesignPatterns.proxy.service;

import DesignPatterns.proxy.payment.PaymentProcessor;

import java.math.BigDecimal;

// Real object: contains the actual business operation.
// It doesn't need to know about the proxy's access-control logic.
public class RealPaymentService implements PaymentProcessor {

    @Override
    public void processPayment(String userRole, BigDecimal amount) {

        System.out.println(
                "REAL SERVICE: Processing payment of INR " + amount
        );
    }
}