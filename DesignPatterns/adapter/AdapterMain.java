package DesignPatterns.adapter;

import DesignPatterns.adapter.adapter.*;
import DesignPatterns.adapter.external.*;
import DesignPatterns.adapter.service.PaymentService;

import java.math.BigDecimal;

/*
 * ADAPTER DESIGN PATTERN
 *
 * Category:
 * Structural Design Pattern
 *
 * Purpose:
 * Make incompatible interfaces work together.
 *
 * Main components:
 * 1. Target:
 *    Interface expected by our application.
 *
 * 2. Adaptee:
 *    Existing incompatible class or external API.
 *
 * 3. Adapter:
 *    Converts the target interface call into
 *    the adaptee's expected call and format.
 *
 * 4. Client:
 *    Uses the target interface.
 *
 * Example:
 * PaymentService
 *       |
 *       v
 * PaymentGateway
 *       |
 *       v
 * RazorpayAdapter / PayPalAdapter
 *       |
 *       v
 * External payment APIs
 *
 * Real-world use:
 * - Payment gateways
 * - Third-party REST APIs
 * - Legacy integrations
 * - Multiple notification providers
 *
 * Interview memory:
 * Adapter = Compatibility.
 * Strategy = Algorithm selection.
 * Factory = Object creation.
 */

public class AdapterMain {

    public static void main(String[] args) {

        // Example 1: Use Razorpay through its adapter.
        RazorpayClient razorpayClient = new RazorpayClient();

        RazorpayAdapter razorpayAdapter =
                new RazorpayAdapter(razorpayClient);

        PaymentService razorpayPaymentService =
                new PaymentService(razorpayAdapter);

        razorpayPaymentService.makePayment(
                new BigDecimal("500.00")
        );

        System.out.println("------------------------");

        // Example 2: Use PayPal through its adapter.
        PayPalClient payPalClient = new PayPalClient();

        PayPalAdapter payPalAdapter =
                new PayPalAdapter(payPalClient);

        PaymentService payPalPaymentService =
                new PaymentService(payPalAdapter);

        payPalPaymentService.makePayment(
                new BigDecimal("1000.00")
        );
    }
}
