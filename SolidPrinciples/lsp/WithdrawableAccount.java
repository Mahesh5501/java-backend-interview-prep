package SolidPrinciples.lsp;

import java.math.BigDecimal;

/*
 * Defines a capability for accounts that support withdrawals.
 *
 * Only account types that can correctly perform withdrawals
 * should implement this interface.
 */
public interface WithdrawableAccount {

    void withdraw(BigDecimal amount);
}
