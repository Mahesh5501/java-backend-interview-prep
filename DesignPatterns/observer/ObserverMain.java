package DesignPatterns.observer;

import DesignPatterns.observer.observer.*;
import DesignPatterns.observer.publisher.TransactionEventPublisher;
import DesignPatterns.observer.service.TransactionService;

import java.math.BigDecimal;

/*
 * OBSERVER DESIGN PATTERN
 *
 * Category:
 * Behavioral Design Pattern
 *
 * Purpose:
 * Notify multiple interested objects when an event occurs.
 *
 * Main components:
 * 1. Subject / Publisher
 *    Maintains observers and publishes events.
 *
 * 2. Observer Interface
 *    Defines the notification contract.
 *
 * 3. Concrete Observers
 *    Implement different reactions to the event.
 *
 * 4. Event
 *    Carries information about what happened.
 *
 * Execution:
 * Transaction completed
 *       |
 *       v
 * Publisher publishes event
 *       |
 *       v
 * Notifies registered observers
 *       |
 *       v
 * Email / SMS / Audit
 *
 * Real-world examples:
 * - Spring ApplicationEventPublisher
 * - Spring @EventListener
 * - UI event listeners
 * - Notification systems
 *
 * SOLID:
 * SRP, OCP, DIP
 *
 * Interview memory:
 * Strategy = Choose one behavior.
 * Observer = Notify multiple interested objects.
 *
 * Kafka has a similar pub-sub concept but provides
 * distributed messaging, persistence and replay.
 */

public class ObserverMain {

    public static void main(String[] args) {

        // Step 1: Create the publisher.
        TransactionEventPublisher publisher =
                new TransactionEventPublisher();

        // Step 2: Create observers.
        EmailNotificationObserver emailObserver =
                new EmailNotificationObserver();

        SmsNotificationObserver smsObserver =
                new SmsNotificationObserver();

        AuditObserver auditObserver =
                new AuditObserver();

        // Step 3: Register observers with the publisher.
        publisher.subscribe(emailObserver);
        publisher.subscribe(smsObserver);
        publisher.subscribe(auditObserver);

        // Step 4: Create the transaction service.
        TransactionService transactionService =
                new TransactionService(publisher);

        // Step 5: Complete a transaction.
        transactionService.completeTransaction(
                "TXN101",
                "ACC501",
                new BigDecimal("5000.00")
        );

        // Step 6: Suppose SMS notifications are no longer required.
        // We can unsubscribe without changing TransactionService.
        publisher.unsubscribe(smsObserver);
    }
}
