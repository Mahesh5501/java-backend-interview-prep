package DesignPatterns.factory;

import DesignPatterns.factory.factory.PaymentFactory;
import DesignPatterns.factory.service.PaymentService;

/*
 * FACTORY METHOD / FACTORY-BASED OBJECT CREATION
 *
 * Category: Creational Design Pattern.
 *
 * Definition:
 * Delegates object creation so the client does not directly depend on concrete implementation classes.
 *
 * Main components:
 * 1. Product interface - defines a common contract.
 * 2. Concrete products - provide specific implementations.
 * 3. Factory - handles object creation.
 * 4. Client/service - uses the returned abstraction.
 *
 * Example:
 * PaymentFactory creates UPI, Card, or Net Banking payment implementations.
 *
 * Benefits:
 * - Separates object creation from business logic.
 * - Reduces direct coupling to concrete classes.
 * - Makes it easier to introduce new implementations.
 *
 * SOLID connection:
 * Supports OCP and DIP when designed appropriately.
 *
 * Important distinction:
 * A switch-based factory is commonly called Simple Factory.
 * Formal GoF Factory Method uses overridable creation methods.
 */
public class FactoryMain {

    public static void main(String[] args) {

        /*
         * Create the factory responsible for payment object creation.
         */
        PaymentFactory paymentFactory = new PaymentFactory();

        /*
         * Inject the factory into PaymentService.
         */
        PaymentService paymentService =
                new PaymentService(paymentFactory);

        /*
         * Process different payment types.
         *
         * The same PaymentService can process UPI, Card,
         * and Net Banking payments.
         */
        paymentService.processPayment("UPI", 1000);

        paymentService.processPayment("CARD", 2000);

        paymentService.processPayment("NET_BANKING", 3000);
    }
}
