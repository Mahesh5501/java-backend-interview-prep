package DesignPatterns.strategy.strategy;

import java.math.BigDecimal;

/*
 * CARD PAYMENT STRATEGY
 *
 * This class contains the algorithm specific to card payments.
 *
 * Card processing may involve card validation, authorization,
 * and communication with a payment gateway.
 *
 * All these details belong to this strategy rather than
 * being mixed into the common PaymentService.
 */
public class CardPaymentStrategy implements PaymentStrategy {

    @Override
    public void processPayment(BigDecimal amount) {

        System.out.println(
                "Processing Card payment of INR " + amount
        );

        System.out.println("Card payment completed successfully");
    }
}
