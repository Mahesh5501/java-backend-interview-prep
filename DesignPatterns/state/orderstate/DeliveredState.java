package DesignPatterns.state.orderstate;

public class DeliveredState extends OrderStateAdapter {

    @Override
    public String getStateName() {
        return "DELIVERED";
    }
}