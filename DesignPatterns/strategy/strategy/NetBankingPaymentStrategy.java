package DesignPatterns.strategy.strategy;

import java.math.BigDecimal;

/*
 * NET BANKING PAYMENT STRATEGY
 *
 * This class contains the algorithm specific to Net Banking.
 *
 * Net Banking can have its own authentication and bank
 * communication flow.
 *
 * By keeping it separate, changes to Net Banking processing
 * do not require changes to UPI or Card processing.
 */
public class NetBankingPaymentStrategy implements PaymentStrategy {

    @Override
    public void processPayment(BigDecimal amount) {

        System.out.println(
                "Processing Net Banking payment of INR " + amount
        );

        System.out.println("Net Banking payment completed successfully");
    }
}
