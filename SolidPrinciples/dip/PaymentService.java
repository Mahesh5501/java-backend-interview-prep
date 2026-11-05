package SolidPrinciples.dip;

import java.math.BigDecimal;

/*
 * High-level business service responsible for processing payments.
 *
 * It depends on the PaymentMethod abstraction rather than
 * directly creating or depending on concrete payment classes.
 *
 * Constructor injection allows us to provide different
 * payment implementations from outside.
 */
public class PaymentService {

    private final PaymentMethod paymentMethod;

    // Inject the required payment implementation through constructor.
    public PaymentService(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    // Process payment using the injected implementation.
    public void processPayment(BigDecimal amount) {

        System.out.println("Payment process started.");

        paymentMethod.pay(amount);

        System.out.println("Payment process completed.");
    }
}
