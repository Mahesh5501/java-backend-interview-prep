package DesignPatterns.adapter.external;

import java.math.BigDecimal;

// Imagine this class comes from the PayPal SDK.
public class PayPalClient {

    public void executePayment(
            BigDecimal amount,
            String currency) {

        System.out.println(
                "PayPal processing payment: "
                        + amount + " " + currency
        );
    }
}
