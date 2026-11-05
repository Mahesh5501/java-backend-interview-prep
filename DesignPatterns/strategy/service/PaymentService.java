package DesignPatterns.strategy.service;

import DesignPatterns.strategy.strategy.PaymentStrategy;

import java.math.BigDecimal;

/*
 * PAYMENT SERVICE - STRATEGY CONTEXT
 *
 * This class represents the Context in the Strategy pattern.
 *
 * WHAT IS THE CONTEXT?
 *
 * The Context is the class that uses a strategy to perform
 * an operation without knowing the internal algorithm.
 *
 * WHY DOES PaymentService DEPEND ON PaymentStrategy?
 *
 * PaymentService should not directly depend on:
 * UpiPaymentStrategy, CardPaymentStrategy, or
 * NetBankingPaymentStrategy.
 *
 * It only needs to know that the supplied strategy can
 * process a payment.
 *
 * BENEFIT:
 * We can change the payment algorithm without modifying
 * the business logic inside this service.
 */
public class PaymentService {

    /*
     * Business operation:
     *
     * The caller supplies the required payment strategy.
     *
     * PaymentService does not decide how UPI, Card, or
     * Net Banking works. It delegates that responsibility
     * to the selected strategy.
     *
     * This makes the strategy interchangeable at runtime.
     */
    public void processPayment(
            PaymentStrategy paymentStrategy,
            BigDecimal amount) {

        /*
         * Basic input validation:
         * Reject a missing strategy or an invalid amount
         * before invoking the payment algorithm.
         */
        if (paymentStrategy == null) {
            throw new IllegalArgumentException(
                    "Payment strategy is required"
            );
        }

        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException(
                    "Payment amount must be greater than zero"
            );
        }

        /*
         * Delegation:
         *
         * Java runtime polymorphism invokes the implementation
         * belonging to the actual strategy object.
         *
         * If UpiPaymentStrategy is supplied, its method runs.
         * If CardPaymentStrategy is supplied, its method runs.
         *
         * PaymentService does not need if-else or switch
         * statements to process each payment algorithm.
         */
        paymentStrategy.processPayment(amount);
    }
}
