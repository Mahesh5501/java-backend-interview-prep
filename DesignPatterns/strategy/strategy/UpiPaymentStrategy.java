package DesignPatterns.strategy.strategy;

import java.math.BigDecimal;

/*
 * UPI PAYMENT STRATEGY
 *
 * This class contains the algorithm specific to UPI payments.
 *
 * WHY A SEPARATE CLASS?
 *
 * UPI processing may involve UPI ID validation, payment app
 * communication, and UPI transaction handling.
 *
 * We keep UPI-specific behavior here instead of adding
 * UPI-related conditions inside PaymentService.
 */
public class UpiPaymentStrategy implements PaymentStrategy {

    /*
     * Implements the common contract with UPI-specific logic.
     */
    @Override
    public void processPayment(BigDecimal amount) {

        System.out.println(
                "Processing UPI payment of INR " + amount
        );

        System.out.println("UPI payment completed successfully");
    }
}
