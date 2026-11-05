package DesignPatterns.chainofresponsibility.handler;

import DesignPatterns.chainofresponsibility.model.TransferRequest;

import java.math.BigDecimal;

public class AmountValidationHandler extends TransferHandler {

    @Override
    public boolean handle(TransferRequest request) {

        System.out.println("Validating transfer amount...");

        if (request.getAmount() == null ||
                request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {

            System.out.println("REJECTED: Invalid transfer amount.");

            return false;
        }

        System.out.println("Amount validation successful.");

        return handleNext(request);
    }
}