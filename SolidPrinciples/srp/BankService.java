package SolidPrinciples.srp;

import java.math.BigDecimal;

/**
 * RESPONSIBILITY: Coordinates the complete deposit use case.
 * SRP: This class coordinates the workflow.
 * It delegates validation, account operations, persistence and notifications to their respective classes.
 */
public class BankService {

    private final DepositValidator depositValidator;
    private final AccountService accountService;
    private final TransactionRepository transactionRepository;
    private final NotificationService notificationService;

    /**
     * Constructor injection: Dependencies are supplied from outside.
     */
    public BankService(
            DepositValidator depositValidator,
            AccountService accountService,
            TransactionRepository transactionRepository,
            NotificationService notificationService) {

        this.depositValidator = depositValidator;
        this.accountService = accountService;
        this.transactionRepository = transactionRepository;
        this.notificationService = notificationService;
    }

    /**
     * Complete deposit workflow.
     */
    public void deposit(BigDecimal amount, String email) {

        System.out.println("\n--- Deposit Process Started ---");

        // Step 1: Validate the deposit amount.
        depositValidator.validate(amount);

        // Step 2: Update the customer's account balance.
        accountService.deposit(amount);

        // Step 3: Save the transaction.
        transactionRepository.save(amount);

        // Step 4: Notify the customer.
        notificationService.sendDepositNotification(email);

        System.out.println("--- Deposit Process Completed ---");
    }
}
