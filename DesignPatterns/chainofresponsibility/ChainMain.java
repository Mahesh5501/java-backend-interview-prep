package DesignPatterns.chainofresponsibility;

import DesignPatterns.chainofresponsibility.handler.AuthenticationHandler;
import DesignPatterns.chainofresponsibility.handler.AmountValidationHandler;
import DesignPatterns.chainofresponsibility.handler.BalanceCheckHandler;
import DesignPatterns.chainofresponsibility.handler.FraudCheckHandler;
import DesignPatterns.chainofresponsibility.handler.TransferHandler;
import DesignPatterns.chainofresponsibility.model.TransferRequest;

import java.math.BigDecimal;

/*
 * CHAIN OF RESPONSIBILITY
 *
 * Category:
 * Behavioral Design Pattern
 *
 * Purpose:
 * Pass a request through multiple handlers.
 *
 * Main components:
 * 1. Handler interface/base class
 * 2. Concrete handlers
 * 3. Reference to the next handler
 *
 * Execution:
 * Request
 *   |
 *   v
 * Handler 1
 *   |
 *   v
 * Handler 2
 *   |
 *   v
 * Handler 3
 *   |
 *   v
 * Result
 *
 * Any handler can:
 * - Process the request.
 * - Forward the request.
 * - Reject or stop processing.
 *
 * Real-world examples:
 * - Spring Security Filter Chain
 * - Servlet Filter Chain
 * - Validation pipelines
 * - Approval workflows
 *
 * Interview memory:
 * Chain = Multiple handlers, one request.
 */
public class ChainMain {

    public static void main(String[] args) {

        // Step 1: Create individual handlers.
        TransferHandler authentication =
                new AuthenticationHandler();

        TransferHandler amountValidation =
                new AmountValidationHandler();

        TransferHandler balanceCheck =
                new BalanceCheckHandler();

        TransferHandler fraudCheck =
                new FraudCheckHandler();

        // Step 2: Connect handlers into a chain.
        authentication
                .setNext(amountValidation)
                .setNext(balanceCheck)
                .setNext(fraudCheck);

        // Step 3: Create a valid transfer request.
        TransferRequest request = new TransferRequest(
                true,
                new BigDecimal("500.00"),
                new BigDecimal("5000.00"),
                false
        );

        // Step 4: Start processing from the first handler.
        boolean approved = authentication.handle(request);

        System.out.println();

        if (approved) {
            System.out.println("Transfer approved. Processing transfer...");
        } else {
            System.out.println("Transfer rejected.");
        }
    }
}