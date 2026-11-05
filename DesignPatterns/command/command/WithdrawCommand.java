package DesignPatterns.command.command;

import DesignPatterns.command.receiver.Wallet;

import java.math.BigDecimal;

public class WithdrawCommand implements WalletCommand {

    private final Wallet wallet;
    private final BigDecimal amount;

    public WithdrawCommand(Wallet wallet, BigDecimal amount) {
        this.wallet = wallet;
        this.amount = amount;
    }

    @Override
    public void execute() {

        // Delegate the actual operation to the Receiver.
        wallet.withdraw(amount);
    }
}