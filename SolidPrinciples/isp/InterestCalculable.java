package SolidPrinciples.isp;

import java.math.BigDecimal;

/*
 * Defines the capability to calculate interest.
 * Only account types responsible for this calculation
 * need to implement this interface.
 */
public interface InterestCalculable {

    BigDecimal calculateInterest();
}