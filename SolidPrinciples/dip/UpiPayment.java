package SolidPrinciples.dip;

import java.math.BigDecimal;

/*
 * Concrete implementation of PaymentMethod.
 *
 * Responsible for processing payments through UPI.
 */
public class UpiPayment implements PaymentMethod {

    @Override
    public void pay(BigDecimal amount) {

        System.out.println(
                "Processing UPI payment: " + amount
        );
    }
}
