package DesignPatterns.command.command;

import DesignPatterns.command.receiver.Wallet;

import java.math.BigDecimal;

public class DepositCommand implements WalletCommand {

    private final Wallet wallet;
    private final BigDecimal amount;

    public DepositCommand(Wallet wallet, BigDecimal amount) {
        this.wallet = wallet;
        this.amount = amount;
    }

    @Override
    public void execute() {

        // Delegate the actual operation to the Receiver.
        wallet.deposit(amount);
    }
}