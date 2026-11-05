package DesignPatterns.chainofresponsibility.model;

import java.math.BigDecimal;

// Represents the request that travels through the chain.
public class TransferRequest {

    private final boolean authenticated;
    private final BigDecimal amount;
    private final BigDecimal availableBalance;
    private final boolean suspicious;

    public TransferRequest(
            boolean authenticated,
            BigDecimal amount,
            BigDecimal availableBalance,
            boolean suspicious) {

        this.authenticated = authenticated;
        this.amount = amount;
        this.availableBalance = availableBalance;
        this.suspicious = suspicious;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public boolean isSuspicious() {
        return suspicious;
    }
}