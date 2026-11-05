package DesignPatterns.factory.factory;

import DesignPatterns.factory.payment.*;

/*
 * PAYMENT FACTORY
 *
 * This class is responsible for creating the appropriate
 * Payment implementation based on the requested payment type.
 *
 * WHY DO WE NEED A FACTORY?
 *
 * Without a factory, PaymentService would need to know
 * how to create UpiPayment, CardPayment, and other objects.
 *
 * By moving object creation into this class, we separate
 * object creation from business logic.
 */
public class PaymentFactory {

    /*
     * Factory method:
     *
     * Accepts the requested payment type and returns
     * the appropriate Payment implementation.
     *
     * Return type is Payment rather than a concrete class
     * because the caller only needs the common abstraction.
     */
    public Payment getPayment(String paymentType) {

        /*
         * Check the requested payment type and create
         * the corresponding implementation.
         *
         * The factory hides the concrete object creation
         * details from the calling service.
         */
        if (paymentType == null) {
            throw new IllegalArgumentException(
                    "Payment type cannot be null"
            );
        }

        switch (paymentType.toUpperCase()) {

            case "UPI":
                return new UpiPayment();

            case "CARD":
                return new CardPayment();

            case "NET_BANKING":
                return new NetBankingPayment();

            default:
                throw new IllegalArgumentException(
                        "Unsupported payment type: " + paymentType
                );
        }
    }
}
