package SolidPrinciples.isp;

import java.math.BigDecimal;

/*
 * Defines the capability to check an account balance.
 */
public interface BalanceReadable {

    BigDecimal getBalance();
}
