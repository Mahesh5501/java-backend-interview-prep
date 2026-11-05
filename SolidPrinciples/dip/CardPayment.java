package SolidPrinciples.dip;

import java.math.BigDecimal;

/*
 * Concrete implementation of PaymentMethod.
 *
 * Responsible for processing payments through cards.
 */
public class CardPayment implements PaymentMethod {

    @Override
    public void pay(BigDecimal amount) {

        System.out.println(
                "Processing Card payment: " + amount
        );
    }
}
