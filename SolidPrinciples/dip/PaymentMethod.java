package SolidPrinciples.dip;

import java.math.BigDecimal;

/*
 * Abstraction for different payment methods.
 *
 * PaymentService depends on this interface instead
 * of depending directly on UpiPayment or CardPayment.
 */
public interface PaymentMethod {

    void pay(BigDecimal amount);
}
