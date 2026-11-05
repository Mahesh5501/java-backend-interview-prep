package DesignPatterns.factory.payment;

/*
 * PAYMENT INTERFACE
 *
 * This interface defines a common contract for all payment methods.
 *
 * WHY DO WE NEED IT?
 *
 * UPI, Card, and Net Banking are different implementations,
 * but all of them perform the common operation of making a payment.
 *
 * Instead of making PaymentService depend on individual classes,
 * we make it depend on this common Payment abstraction.
 *
 * This helps achieve loose coupling and supports polymorphism.
 */
public interface Payment {

    /*
     * Every payment implementation must provide its own
     * payment processing logic.
     */
    void pay(double amount);
}
