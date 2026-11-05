package DesignPatterns.state;

import DesignPatterns.state.model.Order;

public class StateMain {

    public static void main(String[] args) {

        Order order = new Order("ORDER-101");

        System.out.println("Initial state: " + order.getCurrentState());

        // CREATED -> PAID
        order.pay();

        // PAID -> SHIPPED
        order.ship();

        // SHIPPED -> DELIVERED
        order.deliver();

        System.out.println("Final state: " + order.getCurrentState());

        // This operation is invalid because the order is DELIVERED.
        try {
            order.cancel();
        } catch (IllegalStateException exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }
}