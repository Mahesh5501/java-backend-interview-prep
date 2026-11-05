package DesignPatterns.adapter.adapter;

import DesignPatterns.adapter.external.PayPalClient;
import DesignPatterns.adapter.gateway.PaymentGateway;

import java.math.BigDecimal;

public class PayPalAdapter implements PaymentGateway {

    private final PayPalClient payPalClient;

    public PayPalAdapter(PayPalClient payPalClient) {
        this.payPalClient = payPalClient;
    }

    @Override
    public void processPayment(BigDecimal amount) {

        // Our application uses INR in this example.
        // PayPal's API requires an amount and currency separately.
        payPalClient.executePayment(amount, "INR");
    }
}
