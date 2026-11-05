package DesignPatterns.observer.publisher;

import DesignPatterns.observer.event.TransactionEvent;
import DesignPatterns.observer.observer.TransactionObserver;

import java.util.ArrayList;
import java.util.List;

public class TransactionEventPublisher {

    // The publisher maintains a list of interested observers.
    private final List<TransactionObserver> observers = new ArrayList<>();

    // Register a new observer.
    public void subscribe(TransactionObserver observer) {
        observers.add(observer);
    }

    // Remove an observer when it no longer wants notifications.
    public void unsubscribe(TransactionObserver observer) {
        observers.remove(observer);
    }

    // Notify every registered observer when an event occurs.
    public void publish(TransactionEvent event) {

        // Iterate over a copy so changes to subscriptions during
        // notification do not cause a ConcurrentModificationException.
        for (TransactionObserver observer : new ArrayList<>(observers)) {

            observer.onTransactionCompleted(event);
        }
    }
}
