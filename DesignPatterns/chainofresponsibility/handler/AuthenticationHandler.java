package DesignPatterns.chainofresponsibility.handler;

import DesignPatterns.chainofresponsibility.model.TransferRequest;

public class AuthenticationHandler extends TransferHandler {

    @Override
    public boolean handle(TransferRequest request) {

        System.out.println("Checking authentication...");

        if (!request.isAuthenticated()) {

            System.out.println("REJECTED: User is not authenticated.");

            return false;
        }

        System.out.println("Authentication successful.");

        // Continue only when authentication succeeds.
        return handleNext(request);
    }
}