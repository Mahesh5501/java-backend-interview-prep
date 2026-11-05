package DesignPatterns.proxy;

import DesignPatterns.proxy.payment.PaymentProcessor;
import DesignPatterns.proxy.proxy.PaymentServiceProxy;
import DesignPatterns.proxy.service.RealPaymentService;

import java.math.BigDecimal;
/*
 * PROXY DESIGN PATTERN
 *
 * Category:
 * Structural Design Pattern
 *
 * Purpose:
 * Control access to an actual object through a substitute.
 *
 * Main components:
 *
 * 1. Subject:
 *    Common interface.
 *
 * 2. Real Subject:
 *    Actual business implementation.
 *
 * 3. Proxy:
 *    Controls access and delegates to the real object.
 *
 * 4. Client:
 *    Uses the common interface.
 *
 * Execution:
 * Client -> Proxy -> Real Object
 *
 * Types:
 * - Protection Proxy
 * - Virtual Proxy
 * - Caching Proxy
 * - Logging Proxy
 * - Remote Proxy
 *
 * Java/Spring examples:
 * - JDK Dynamic Proxy
 * - CGLIB
 * - Spring AOP
 * - @Transactional
 *
 * Interview memory:
 * Proxy = Control access.
 * Decorator = Add behavior.
 * Adapter = Make interfaces compatible.
 */

public class ProxyMain {

    public static void main(String[] args) {

        // Step 1: Create the actual payment service.
        PaymentProcessor realService =
                new RealPaymentService();

        // Step 2: Wrap the real service with a proxy.
        PaymentProcessor paymentService =
                new PaymentServiceProxy(realService);

        System.out.println("---- Authorized request ----");

        // Step 3: Authorized user.
        paymentService.processPayment(
                "ADMIN",
                new BigDecimal("500.00")
        );

        System.out.println();

        System.out.println("---- Unauthorized request ----");

        // Step 4: Unauthorized user.
        paymentService.processPayment(
                "CUSTOMER",
                new BigDecimal("1000.00")
        );
    }
}