package DesignPatterns.observer.observer;

import DesignPatterns.observer.event.TransactionEvent;

public class SmsNotificationObserver implements TransactionObserver {

    @Override
    public void onTransactionCompleted(TransactionEvent event) {

        // SMS-specific logic belongs inside this observer.
        System.out.println(
                "SMS: Transaction " + event.getTransactionId()
                        + " completed. Amount: " + event.getAmount()
        );
    }
}
