package DesignPatterns.command;

import DesignPatterns.command.command.DepositCommand;
import DesignPatterns.command.command.WithdrawCommand;
import DesignPatterns.command.invoker.CommandProcessor;
import DesignPatterns.command.receiver.Wallet;

import java.math.BigDecimal;

public class CommandMain {

    public static void main(String[] args) {

        // Receiver: the object that performs actual wallet operations.
        Wallet wallet = new Wallet(
                "WALLET-101",
                new BigDecimal("5000.00")
        );

        // Invoker: responsible for executing commands.
        CommandProcessor processor = new CommandProcessor();

        // Create a deposit command.
        WalletCommandExample(processor, wallet);
    }

    private static void WalletCommandExample(
            CommandProcessor processor,
            Wallet wallet) {

        // Concrete Command: deposit request.
        DepositCommand depositCommand = new DepositCommand(
                wallet,
                new BigDecimal("1000.00")
        );

        // Invoker executes the deposit command.
        processor.process(depositCommand);

        System.out.println();

        // Concrete Command: withdrawal request.
        WithdrawCommand withdrawCommand = new WithdrawCommand(
                wallet,
                new BigDecimal("500.00")
        );

        // Same Invoker, different command.
        processor.process(withdrawCommand);

        System.out.println();
        System.out.println("Final wallet balance: " + wallet.getBalance());
    }
}