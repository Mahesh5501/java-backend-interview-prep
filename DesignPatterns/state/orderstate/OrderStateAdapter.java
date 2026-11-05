package DesignPatterns.state.orderstate;

import DesignPatterns.state.model.Order;

public abstract class OrderStateAdapter implements OrderState {

    @Override
    public void pay(Order order) {
        throw new IllegalStateException(
                "Payment is not allowed in " + getStateName() + " state"
        );
    }

    @Override
    public void ship(Order order) {
        throw new IllegalStateException(
                "Shipping is not allowed in " + getStateName() + " state"
        );
    }

    @Override
    public void deliver(Order order) {
        throw new IllegalStateException(
                "Delivery is not allowed in " + getStateName() + " state"
        );
    }

    @Override
    public void cancel(Order order) {
        throw new IllegalStateException(
                "Cancellation is not allowed in " + getStateName() + " state"
        );
    }
}