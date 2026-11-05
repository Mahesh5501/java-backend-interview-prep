package DesignPatterns.observer.service;

import DesignPatterns.observer.event.TransactionEvent;
import DesignPatterns.observer.publisher.TransactionEventPublisher;

import java.math.BigDecimal;

public class TransactionService {

    // TransactionService depends on the publisher, not individual observers.
    private final TransactionEventPublisher eventPublisher;

    public TransactionService(TransactionEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void completeTransaction(
            String transactionId,
            String accountId,
            BigDecimal amount) {

        // Step 1: Complete the actual transaction.
        System.out.println("Processing wallet transaction...");

        // In a real application, this is where successful
        // database and payment processing would happen.

        System.out.println("Transaction completed successfully.");

        // Step 2: Create an event after successful completion.
        TransactionEvent event = new TransactionEvent(
                transactionId,
                accountId,
                amount,
                "COMPLETED"
        );

        // Step 3: Publish the event.
        // All registered observers will be notified.
        eventPublisher.publish(event);
    }
}
