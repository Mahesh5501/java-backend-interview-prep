package DesignPatterns.state.orderstate;

import DesignPatterns.state.model.Order;

public class ShippedState extends OrderStateAdapter {

    @Override
    public void deliver(Order order) {
        System.out.println("Order delivered: " + order.getOrderId());

        order.setState(new DeliveredState());
    }

    @Override
    public String getStateName() {
        return "SHIPPED";
    }
}