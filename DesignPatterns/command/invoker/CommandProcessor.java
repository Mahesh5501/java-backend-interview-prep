package DesignPatterns.command.invoker;

import DesignPatterns.command.command.WalletCommand;

public class CommandProcessor {

    public void process(WalletCommand command) {

        System.out.println("Processing wallet command...");

        // Invoker executes the command without knowing its implementation.
        command.execute();

        System.out.println("Command execution completed");
    }
}