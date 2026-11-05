package DesignPatterns.facade;

import DesignPatterns.facade.facade.WalletTransferFacade;
import DesignPatterns.facade.subsystem.*;

import java.math.BigDecimal;

public class FacadeMain {

    public static void main(String[] args) {

        // Create subsystem objects.
        AccountService accountService = new AccountService();
        BalanceService balanceService = new BalanceService();
        FraudDetectionService fraudDetectionService =
                new FraudDetectionService();
        LedgerService ledgerService = new LedgerService();
        NotificationService notificationService =
                new NotificationService();

        // Create the Facade and inject its dependencies.
        WalletTransferFacade facade = new WalletTransferFacade(
                accountService,
                balanceService,
                fraudDetectionService,
                ledgerService,
                notificationService
        );

        // Client only needs to know about this one method.
        facade.transfer(
                "WALLET-101",
                "WALLET-202",
                new BigDecimal("1000.00")
        );
    }
}