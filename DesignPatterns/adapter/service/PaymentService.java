package DesignPatterns.adapter.service;

import DesignPatterns.adapter.gateway.PaymentGateway;

import java.math.BigDecimal;

public class PaymentService {

    // The service depends only on the common interface.
    // It doesn't know anything about Razorpay or PayPal.
    private final PaymentGateway paymentGateway;

    public PaymentService(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public void makePayment(BigDecimal amount) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Payment amount must be greater than zero"
            );
        }

        // The adapter handles the provider-specific conversion.
        paymentGateway.processPayment(amount);
    }
}