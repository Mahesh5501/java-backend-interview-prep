package DesignPatterns.decorator;

import DesignPatterns.decorator.decorator.*;
import DesignPatterns.decorator.payment.*;

import java.math.BigDecimal;
/*
 * DECORATOR DESIGN PATTERN
 *
 * Category:
 * Structural Design Pattern
 *
 * Purpose:
 * Add behavior without modifying the original class.
 *
 * Main components:
 * 1. Component:
 *    Common interface.
 *
 * 2. Concrete Component:
 *    Original implementation.
 *
 * 3. Decorator:
 *    Implements the same interface and holds
 *    another component.
 *
 * 4. Concrete Decorators:
 *    Add extra behavior before/after delegation.
 *
 * Example:
 * BasicPaymentProcessor
 *       |
 *       v
 * LoggingDecorator
 *       |
 *       v
 * ValidationDecorator
 *       |
 *       v
 * MetricsDecorator
 *
 * Real-world examples:
 * - Java I/O streams
 * - Logging wrappers
 * - Metrics wrappers
 * - Additional validation behavior
 *
 * Interview memory:
 * Decorator = Same interface + wrapping + extra behavior.
 */

public class DecoratorMain {

    public static void main(String[] args) {

        // Step 1: Create the original payment processor.
        PaymentProcessor processor =
                new BasicPaymentProcessor();

        // Step 2: Add logging behavior.
        processor = new LoggingDecorator(processor);

        // Step 3: Add validation behavior.
        processor = new ValidationDecorator(processor);

        // Step 4: Add metrics behavior.
        processor = new MetricsDecorator(processor);

        // Step 5: Execute the combined processor.
        processor.processPayment(
                new BigDecimal("500.00")
        );
    }
}
