package DesignPatterns.observer.event;

import java.math.BigDecimal;

// This object carries the information about a completed transaction.
// Observers receive this event and decide what action they need to perform.
public class TransactionEvent {

    private final String transactionId;
    private final String accountId;
    private final BigDecimal amount;
    private final String status;

    public TransactionEvent(
            String transactionId,
            String accountId,
            BigDecimal amount,
            String status) {

        this.transactionId = transactionId;
        this.accountId = accountId;
        this.amount = amount;
        this.status = status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getAccountId() {
        return accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }
}
