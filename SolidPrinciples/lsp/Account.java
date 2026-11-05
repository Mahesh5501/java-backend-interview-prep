package SolidPrinciples.lsp;

import java.math.BigDecimal;

/*
 * Common parent class for all account types.
 *
 * Every account has a balance and supports deposits.
 * We do not define withdrawal here because not every
 * account type necessarily supports withdrawals.
 */
public abstract class Account {

    protected BigDecimal balance;

    public Account(BigDecimal initialBalance) {
        this.balance = initialBalance;
    }

    // Common behavior supported by every account.
    public void deposit(BigDecimal amount) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Invalid deposit amount");
        }

        balance = balance.add(amount);

        System.out.println("Amount deposited: " + amount);
    }

    // Returns the current account balance.
    public BigDecimal getBalance() {
        return balance;
    }
}
