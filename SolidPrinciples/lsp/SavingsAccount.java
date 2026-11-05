package SolidPrinciples.lsp;

import java.math.BigDecimal;

/*
 * SavingsAccount supports both deposits and withdrawals.
 *
 * It inherits common account behavior and implements
 * the withdrawal capability.
 */
public class SavingsAccount extends Account
        implements WithdrawableAccount {

    public SavingsAccount(BigDecimal initialBalance) {
        super(initialBalance);
    }

    @Override
    public void withdraw(BigDecimal amount) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Invalid withdrawal amount");
        }

        if (amount.compareTo(balance) > 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        balance = balance.subtract(amount);

        System.out.println("Amount withdrawn: " + amount);
    }
}
