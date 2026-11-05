package DesignPatterns.chainofresponsibility.handler;

import DesignPatterns.chainofresponsibility.model.TransferRequest;

public class BalanceCheckHandler extends TransferHandler {

    @Override
    public boolean handle(TransferRequest request) {

        System.out.println("Checking wallet balance...");

        if (request.getAvailableBalance() == null ||
                request.getAvailableBalance().compareTo(
                        request.getAmount()) < 0) {

            System.out.println("REJECTED: Insufficient wallet balance.");

            return false;
        }

        System.out.println("Balance check successful.");

        return handleNext(request);
    }
}