package DesignPatterns.adapter.adapter;

import DesignPatterns.adapter.external.RazorpayClient;
import DesignPatterns.adapter.gateway.PaymentGateway;

import java.math.BigDecimal;

public class RazorpayAdapter implements PaymentGateway {

    // The adapter holds the existing third-party client.
    private final RazorpayClient razorpayClient;

    public RazorpayAdapter(RazorpayClient razorpayClient) {
        this.razorpayClient = razorpayClient;
    }

    @Override
    public void processPayment(BigDecimal amount) {

        // Our application provides rupees as BigDecimal.
        // Razorpay expects paise as a long.
        long amountInPaise = amount
                .multiply(new BigDecimal("100"))
                .longValueExact();

        // Convert our application's request into Razorpay's API format.
        razorpayClient.createPayment(amountInPaise);
    }
}
