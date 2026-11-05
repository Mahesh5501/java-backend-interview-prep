package SolidPrinciples.srp;

import java.math.BigDecimal;

/**
 * RESPONSIBILITY: Handles account balance-related business operations.
 * SRP: This class does not validate deposits, save transactions, or send notifications. It focuses on account operations.
 */
public class AccountService {

    // Simulating an account's existing balance.
    private BigDecimal balance = new BigDecimal("5000");

    /**
     * Adds the deposited amount to the existing balance.
     *
     * @param amount deposit amount
     */
    public void deposit(BigDecimal amount) {

        balance = balance.add(amount);

        System.out.println(
                "Account balance updated successfully.");

        System.out.println(
                "Current balance: " + balance);
    }

    /**
     * Returns the current account balance.
     */
    public BigDecimal getBalance() {
        return balance;
    }
}

