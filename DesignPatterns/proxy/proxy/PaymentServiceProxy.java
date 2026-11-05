package DesignPatterns.proxy.proxy;

import DesignPatterns.proxy.payment.PaymentProcessor;

import java.math.BigDecimal;

// Proxy implements the same interface as the real service.
// It controls access before forwarding requests to the real object.
public class PaymentServiceProxy implements PaymentProcessor {

    private final PaymentProcessor realPaymentService;

    public PaymentServiceProxy(PaymentProcessor realPaymentService) {
        this.realPaymentService = realPaymentService;
    }

    @Override
    public void processPayment(String userRole, BigDecimal amount) {

        // Step 1: Perform authorization before accessing the real service.
        if (!"ADMIN".equals(userRole)) {

            System.out.println(
                    "PROXY: Access denied. User is not authorized."
            );

            return;
        }

        // Step 2: Log the request.
        System.out.println("PROXY: Payment request received.");

        // Step 3: Delegate to the actual payment service.
        realPaymentService.processPayment(userRole, amount);

        // Step 4: Perform additional work after the real operation.
        System.out.println("PROXY: Payment request completed.");
    }
}