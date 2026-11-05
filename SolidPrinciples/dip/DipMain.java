package SolidPrinciples.dip;

import java.math.BigDecimal;

/*

DIP = Depend on Abstractions
- High-level classes should not directly depend on low-level classes.
- Both should depend on interfaces or abstractions.
- Avoid creating concrete dependencies directly inside business services.
- Use constructor injection to provide dependencies.
Remember: Depend on interfaces, not concrete implementations.

 * DIP (Dependency Inversion Principle):
 *
 * High-level classes should not directly depend on low-level
 * classes. Both should depend on abstractions (interfaces).
 *
 * Abstractions should not depend on implementation details.
 * Implementations should depend on abstractions.
 *
 * Example: PaymentService should depend on a PaymentMethod
 * interface instead of directly depending on UpiPayment
 * or CardPayment classes.
 */
public class DipMain {

    public static void main(String[] args) {

        // Create UPI payment implementation.
        PaymentMethod upiPayment = new UpiPayment();

        // Inject UPI implementation into PaymentService.
        PaymentService upiService = new PaymentService(upiPayment);

        // Process UPI payment.
        upiService.processPayment(new BigDecimal("1000"));

        System.out.println("-------------------------");

        // Create Card payment implementation.
        PaymentMethod cardPayment = new CardPayment();

        // Inject Card implementation into PaymentService.
        PaymentService cardService = new PaymentService(cardPayment);

        // Process Card payment.
        cardService.processPayment(new BigDecimal("2000"));
    }
}
