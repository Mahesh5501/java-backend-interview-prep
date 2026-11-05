package DesignPatterns.observer.observer;

import DesignPatterns.observer.event.TransactionEvent;

// Common contract for every observer.
// Any component interested in transaction completion must implement this method.
public interface TransactionObserver {

    void onTransactionCompleted(TransactionEvent event);
}
