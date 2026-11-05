package SolidPrinciples.lsp;

import java.math.BigDecimal;

/*
 * This service handles withdrawals.
 *
 * It accepts only accounts that support withdrawals.
 * It does not depend on a specific account implementation.
 */
public class WithdrawalService {

    public void processWithdrawal(
            WithdrawableAccount account,
            BigDecimal amount) {

        account.withdraw(amount);
    }
}
