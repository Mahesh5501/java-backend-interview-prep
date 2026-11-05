package DesignPatterns.chainofresponsibility.handler;

import DesignPatterns.chainofresponsibility.model.TransferRequest;

// Defines the common contract and connection between handlers.
public abstract class TransferHandler {

    // Reference to the next handler in the chain.
    protected TransferHandler nextHandler;

    // Allows us to configure the next handler.
    public TransferHandler setNext(TransferHandler nextHandler) {

        this.nextHandler = nextHandler;

        // Returning the next handler makes chain construction convenient.
        return nextHandler;
    }

    // Every concrete handler implements its own validation.
    public abstract boolean handle(TransferRequest request);

    // Continue processing when the current handler succeeds.
    protected boolean handleNext(TransferRequest request) {

        // If there is no next handler, the chain has completed.
        if (nextHandler == null) {
            return true;
        }

        return nextHandler.handle(request);
    }
}