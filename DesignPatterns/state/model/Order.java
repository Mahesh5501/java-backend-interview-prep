package DesignPatterns.state.model;

import DesignPatterns.state.orderstate.CreatedState;
import DesignPatterns.state.orderstate.OrderState;

public class Order {

    private final String orderId;

    // The current state controls which operations are allowed.
    private OrderState currentState;

    public Order(String orderId) {
        this.orderId = orderId;

        // Every new order starts in CREATED state.
        this.currentState = new CreatedState();
    }

    public void pay() {
        currentState.pay(this);
    }

    public void ship() {
        currentState.ship(this);
    }

    public void deliver() {
        currentState.deliver(this);
    }

    public void cancel() {
        currentState.cancel(this);
    }

    // State objects call this when a valid transition happens.
    public void setState(OrderState newState) {
        this.currentState = newState;
        System.out.println("Order state changed to: " + newState.getStateName());
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCurrentState() {
        return currentState.getStateName();
    }
}