package DesignPatterns.factory.payment;

/*
 * NET BANKING PAYMENT IMPLEMENTATION
 *
 * This class provides the Net Banking-specific payment logic.
 *
 * We can introduce new payment methods by creating new
 * implementations of Payment.
 */
public class NetBankingPayment implements Payment {

    @Override
    public void pay(double amount) {

        System.out.println(
                "Processing Net Banking payment of amount: " + amount
        );
    }
}