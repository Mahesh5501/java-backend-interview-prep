package DesignPatterns.factory.payment;

/*
 * UPI PAYMENT IMPLEMENTATION
 *
 * This class contains the payment processing logic specific to UPI.
 *
 * WHY DO WE CREATE A SEPARATE CLASS?
 *
 * Each payment method may have different processing logic.
 * Keeping that logic separate makes the application easier
 * to maintain and extend.
 */
public class UpiPayment implements Payment {

    /*
     * Implements the common payment contract using UPI logic.
     */
    @Override
    public void pay(double amount) {

        System.out.println(
                "Processing UPI payment of amount: " + amount
        );
    }
}
