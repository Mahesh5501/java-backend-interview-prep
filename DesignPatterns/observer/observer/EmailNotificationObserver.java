package DesignPatterns.observer.observer;

import DesignPatterns.observer.event.TransactionEvent;

public class EmailNotificationObserver implements TransactionObserver {

    @Override
    public void onTransactionCompleted(TransactionEvent event) {

        // Email-specific logic belongs inside this observer.
        System.out.println(
                "EMAIL: Transaction " + event.getTransactionId()
                        + " completed successfully."
        );
    }
}
