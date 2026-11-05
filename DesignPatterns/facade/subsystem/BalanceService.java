package DesignPatterns.facade.subsystem;

import java.math.BigDecimal;

public class BalanceService {

    public void checkBalance(String accountId, BigDecimal amount) {

        // Demo balance only. A real application would query the account
        // balance from its database or account service.
        BigDecimal availableBalance = new BigDecimal("5000.00");

        if (amount == null || amount.signum() <= 0
                || availableBalance.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance or invalid amount");
        }

        System.out.println("Balance checked for " + accountId);
    }
}