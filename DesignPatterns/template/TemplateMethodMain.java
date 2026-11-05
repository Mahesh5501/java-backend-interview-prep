package DesignPatterns.template;

import DesignPatterns.template.payment.*;

import java.math.BigDecimal;
/*
 * TEMPLATE METHOD
 *
 * Category: Behavioral
 *
 * Purpose:
 * Define the algorithm skeleton in a parent class.
 *
 * Main concept:
 * Parent controls the workflow.
 * Child classes customize specific steps.
 *
 * Important:
 * Template method is commonly declared final
 * to protect the workflow sequence.
 *
 * Example:
 * Payment:
 * 1. Validate
 * 2. Authenticate
 * 3. Execute
 * 4. Generate receipt
 *
 * Interview memory:
 * Fixed workflow + customizable steps.
 */

public class TemplateMethodMain {

    public static void main(String[] args) {

        System.out.println("---- UPI Payment ----");

        PaymentTemplate upiPayment = new UpiPayment();

        upiPayment.processPayment(
                new BigDecimal("500.00")
        );

        System.out.println();

        System.out.println("---- Card Payment ----");

        PaymentTemplate cardPayment = new CardPayment();

        cardPayment.processPayment(
                new BigDecimal("1000.00")
        );
    }
}
