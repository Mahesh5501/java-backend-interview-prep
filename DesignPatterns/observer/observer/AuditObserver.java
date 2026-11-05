package DesignPatterns.observer.observer;

import DesignPatterns.observer.event.TransactionEvent;

public class AuditObserver implements TransactionObserver {

    @Override
    public void onTransactionCompleted(TransactionEvent event) {

        // Audit-specific logic belongs inside this observer.
        System.out.println(
                "AUDIT: Recorded transaction "
                        + event.getTransactionId()
                        + " for account "
                        + event.getAccountId()
        );
    }
}
