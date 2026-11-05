package DesignPatterns.state.orderstate;

import DesignPatterns.state.model.Order;

public class CreatedState extends OrderStateAdapter {

    @Override
    public void pay(Order order) {
        System.out.println("Payment successful for " + order.getOrderId());

        order.setState(new PaidState());
    }

    @Override
    public void cancel(Order order) {
        System.out.println("Order cancelled: " + order.getOrderId());

        order.setState(new CancelledState());
    }

    @Override
    public String getStateName() {
        return "CREATED";
    }
}