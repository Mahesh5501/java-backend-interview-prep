package DesignPatterns.state.orderstate;

public class CancelledState extends OrderStateAdapter {

    @Override
    public String getStateName() {
        return "CANCELLED";
    }
}