package SolidPrinciples.isp;

import java.math.BigDecimal;

/*
 * Defines only the withdrawal capability.
 * Only accounts that support withdrawals implement it.
 */
public interface Withdrawable {

    void withdraw(BigDecimal amount);
}
