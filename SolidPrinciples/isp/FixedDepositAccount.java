package SolidPrinciples.isp;

import java.math.BigDecimal;

/*
 * FixedDepositAccount supports:
 * 1. Deposits
 * 2. Balance enquiry
 * 3. Interest calculation
 *
 * It does not implement Withdrawable because it does not
 * support normal withdrawals before maturity.
 */
public class FixedDepositAccount implements
        Depositable, BalanceReadable, InterestCalculable {

    private BigDecimal balance;

    public FixedDepositAccount(BigDecimal initialBalance) {
        this.balance = initialBalance;
    }

    @Override
    public void deposit(BigDecimal amount) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Invalid deposit amount");
        }

        balance = balance.add(amount);

        System.out.println("Fixed Deposit amount added: " + amount);
    }

    @Override
    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public BigDecimal calculateInterest() {

        // Learning example: calculate simple interest at 5%.
        return balance.multiply(new BigDecimal("0.05"));
    }
}
