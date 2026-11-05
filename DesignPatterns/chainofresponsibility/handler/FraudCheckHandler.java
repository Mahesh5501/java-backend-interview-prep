package DesignPatterns.chainofresponsibility.handler;

import DesignPatterns.chainofresponsibility.model.TransferRequest;

public class FraudCheckHandler extends TransferHandler {

    @Override
    public boolean handle(TransferRequest request) {

        System.out.println("Performing fraud check...");

        if (request.isSuspicious()) {

            System.out.println("REJECTED: Suspicious transaction detected.");

            return false;
        }

        System.out.println("Fraud check successful.");

        return handleNext(request);
    }
}