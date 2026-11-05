package DesignPatterns.command.receiver;

import java.math.BigDecimal;

public class Wallet {

    private final String accountId;
    private BigDecimal balance;

    public Wallet(String accountId, BigDecimal initialBalance) {
        this.accountId = accountId;
        this.balance = initialBalance;
    }

    public void deposit(BigDecimal amount) {

        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("Invalid deposit amount");
        }

        balance = balance.add(amount);

        System.out.println("Deposited " + amount);
        System.out.println("Current balance: " + balance);
    }

    public void withdraw(BigDecimal amount) {

        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("Invalid withdrawal amount");
        }

        if (balance.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        balance = balance.subtract(amount);

        System.out.println("Withdrawn " + amount);
        System.out.println("Current balance: " + balance);
    }

    public String getAccountId() {
        return accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}