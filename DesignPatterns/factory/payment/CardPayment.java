package DesignPatterns.factory.payment;

/*
 * CARD PAYMENT IMPLEMENTATION
 *
 * This class handles payment processing specific to cards.
 *
 * It implements Payment, so it can be used wherever
 * a Payment reference is expected.
 */
public class CardPayment implements Payment {

    @Override
    public void pay(double amount) {

        System.out.println(
                "Processing Card payment of amount: " + amount
        );
    }
}
