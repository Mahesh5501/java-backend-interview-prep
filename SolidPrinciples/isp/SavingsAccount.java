package SolidPrinciples.isp;

import java.math.BigDecimal;

/*
 * SavingsAccount supports:
 * 1. Deposits
 * 2. Withdrawals
 * 3. Balance enquiry
 *
 * It implements only the interfaces representing
 * capabilities that it actually supports.
 */
public class SavingsAccount implements
        Depositable, Withdrawable, BalanceReadable {

    private BigDecimal balance;

    public SavingsAccount(BigDecimal initialBalance) {
        this.balance = initialBalance;
    }

    @Override
    public void deposit(BigDecimal amount) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Invalid deposit amount");
        }

        balance = balance.add(amount);

        System.out.println("Savings deposit: " + amount);
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

        System.out.println("Savings withdrawal: " + amount);
    }

    @Override
    public BigDecimal getBalance() {
        return balance;
    }
}
