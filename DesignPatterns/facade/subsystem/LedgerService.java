package DesignPatterns.facade.subsystem;

import java.math.BigDecimal;

public class LedgerService {

    public void debit(String accountId, BigDecimal amount) {
        System.out.println("Debited " + amount + " from " + accountId);
    }

    public void credit(String accountId, BigDecimal amount) {
        System.out.println("Credited " + amount + " to " + accountId);
    }
}