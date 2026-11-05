package SolidPrinciples.isp;

import java.math.BigDecimal;

/*
 * Defines only the deposit capability.
 * Accounts that support deposits implement this interface.
 */
public interface Depositable {

    void deposit(BigDecimal amount);
}
