package DesignPatterns.facade.facade;

import DesignPatterns.facade.subsystem.*;

import java.math.BigDecimal;

public class WalletTransferFacade {

    // Facade knows the subsystem services and coordinates them.
    private final AccountService accountService;
    private final BalanceService balanceService;
    private final FraudDetectionService fraudDetectionService;
    private final LedgerService ledgerService;
    private final NotificationService notificationService;

    public WalletTransferFacade(
            AccountService accountService,
            BalanceService balanceService,
            FraudDetectionService fraudDetectionService,
            LedgerService ledgerService,
            NotificationService notificationService) {

        this.accountService = accountService;
        this.balanceService = balanceService;
        this.fraudDetectionService = fraudDetectionService;
        this.ledgerService = ledgerService;
        this.notificationService = notificationService;
    }

    // The client calls one method instead of coordinating five services.
    public void transfer(String fromAccount,
                         String toAccount,
                         BigDecimal amount) {

        System.out.println("Starting wallet transfer...");

        // Step 1: Validate accounts.
        accountService.validateAccounts(fromAccount, toAccount);

        // Step 2: Check sender's balance.
        balanceService.checkBalance(fromAccount, amount);

        // Step 3: Run fraud checks.
        fraudDetectionService.checkTransfer(fromAccount, toAccount, amount);

        // Step 4: Update ledger.
        ledgerService.debit(fromAccount, amount);
        ledgerService.credit(toAccount, amount);

        // Step 5: Notify users.
        notificationService.sendNotification(fromAccount, toAccount, amount);

        System.out.println("Transfer completed successfully");
    }
}