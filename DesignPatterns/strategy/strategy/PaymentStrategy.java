package DesignPatterns.strategy.strategy;

import java.math.BigDecimal;

/*
 * PAYMENT STRATEGY INTERFACE
 *
 * This interface defines the common contract for all payment
 * processing strategies.
 *
 * WHY DO WE NEED THIS INTERFACE?
 *
 * UPI, Card, and Net Banking have different processing logic,
 * but they all perform the same high-level operation:
 * processing a payment.
 *
 * Instead of writing all algorithms inside PaymentService,
 * we define a common contract here.
 *
 * Every payment strategy must implement processPayment().
 *
 * BENEFIT:
 * PaymentService can work with any implementation of this
 * interface without knowing its concrete class.
 */
public interface PaymentStrategy {

    /*
     * Each strategy provides its own implementation of payment processing.
     * BigDecimal is used instead of double because monetary calculations should avoid floating-point precision issues.
     */
    void processPayment(BigDecimal amount);
}
