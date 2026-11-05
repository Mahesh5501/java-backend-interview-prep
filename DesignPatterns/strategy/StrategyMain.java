package DesignPatterns.strategy;

import DesignPatterns.strategy.service.PaymentService;
import DesignPatterns.strategy.strategy.*;

import java.math.BigDecimal;

/*
 * STRATEGY DESIGN PATTERN
 *
 * Category: Behavioral Design Pattern.
 *
 * Definition:
 * Defines a family of algorithms, encapsulates each algorithm
 * in a separate class, and makes them interchangeable.
 *
 * Problem solved:
 * Avoids large if-else or switch blocks containing
 * multiple business algorithms.
 *
 * Main components:
 * 1. Strategy interface - defines a common contract.
 * 2. Concrete strategies - implement different algorithms.
 * 3. Context - uses the selected strategy.
 * 4. Client - selects and supplies the strategy.
 *
 * Example:
 * PaymentService can process UPI, Card, and Net Banking
 * payments using different strategy implementations.
 *
 * Important concepts:
 * - Uses composition and runtime polymorphism.
 * - Strategies can be changed at runtime.
 * - Each strategy handles one specific algorithm.
 * - Context depends on an abstraction, not concrete classes.
 *
 * SOLID connection:
 * Supports SRP, OCP, LSP, ISP, and DIP when designed properly.
 *
 * Spring Boot:
 * Multiple strategy implementations can be registered
 * as Spring beans and selected using dependency injection.
 *
 * Difference:
 * Factory focuses on object creation.
 * Strategy focuses on interchangeable behavior/algorithms.
 */
public class StrategyMain {

    public static void main(String[] args) {

        /*
         * Create the Context.
         *
         * PaymentService contains the common business operation.
         * It does not contain individual payment algorithms.
         */
        PaymentService paymentService = new PaymentService();

        /*
         * Select the UPI strategy.
         *
         * The variable uses the PaymentStrategy interface,
         * even though the actual object is UpiPaymentStrategy.
         */
        PaymentStrategy upiStrategy = new UpiPaymentStrategy();

        System.out.println("===== UPI PAYMENT =====");

        paymentService.processPayment(
                upiStrategy,
                new BigDecimal("1000.00")
        );

        /*
         * Select the Card strategy.
         *
         * We are passing a different implementation of the
         * same interface to the same PaymentService.
         */
        PaymentStrategy cardStrategy = new CardPaymentStrategy();

        System.out.println("\n===== CARD PAYMENT =====");

        paymentService.processPayment(
                cardStrategy,
                new BigDecimal("2000.00")
        );

        /*
         * Select the Net Banking strategy.
         *
         * Notice that PaymentService has not changed.
         * Only the strategy implementation is different.
         */
        PaymentStrategy netBankingStrategy =
                new NetBankingPaymentStrategy();

        System.out.println("\n===== NET BANKING PAYMENT =====");

        paymentService.processPayment(
                netBankingStrategy,
                new BigDecimal("3000.00")
        );
    }
}
