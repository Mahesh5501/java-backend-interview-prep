package DesignPatterns.state.orderstate;

import DesignPatterns.state.model.Order;

public class PaidState extends OrderStateAdapter {

    @Override
    public void ship(Order order) {
        System.out.println("Shipping order: " + order.getOrderId());

        order.setState(new ShippedState());
    }

    @Override
    public void cancel(Order order) {
        System.out.println("Paid order cancelled: " + order.getOrderId());

        order.setState(new CancelledState());
    }

    @Override
    public String getStateName() {
        return "PAID";
    }
}