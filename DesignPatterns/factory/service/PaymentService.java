package DesignPatterns.factory.service;

import DesignPatterns.factory.factory.PaymentFactory;
import DesignPatterns.factory.payment.*;

/*
 * PAYMENT SERVICE
 *
 * This class handles the business operation of processing payments.
 *
 * IMPORTANT:
 *
 * PaymentService does not directly create UpiPayment,
 * CardPayment, or NetBankingPayment objects.
 *
 * It asks PaymentFactory to provide the appropriate object.
 *
 * This separates business logic from object creation.
 */
public class PaymentService {

    /*
     * Factory dependency:
     * Responsible for providing the appropriate Payment object.
     */
    private final PaymentFactory paymentFactory;

    /*
     * Constructor injection:
     * The factory is provided from outside instead of being
     * created inside the service.
     */
    public PaymentService(PaymentFactory paymentFactory) {
        this.paymentFactory = paymentFactory;
    }

    /*
     * Business method:
     * Receives the payment type and amount.
     *
     * 1. Requests the correct payment implementation.
     * 2. Receives the Payment object.
     * 3. Calls the common pay() method.
     *
     * The service does not need to know which concrete
     * payment implementation it received.
     */
    public void processPayment(String paymentType, double amount) {

        Payment payment = paymentFactory.getPayment(paymentType);

        payment.pay(amount);
    }
}
