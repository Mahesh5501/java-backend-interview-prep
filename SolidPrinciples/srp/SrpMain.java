package SolidPrinciples.srp;

import java.math.BigDecimal;

/*
 * SRP (Single Responsibility Principle):
 *
 * A class should have only one responsibility.
 * It should handle one specific part of the application and have only one reason to change.
 *
 * Example: Validation, account management, database operations, and notifications should be handled by separate classes.
 */

/**
 * MAIN CLASS:
 * Demonstrates the Single Responsibility Principle.
 * We create the required objects, inject dependencies into BankService, and execute a deposit operation.
 */
public class SrpMain {

    public static void main(String[] args) {

        // Step 1: Create individual responsibility classes.
        DepositValidator validator = new DepositValidator();

        AccountService accountService = new AccountService();

        TransactionRepository repository =
                new TransactionRepository();

        NotificationService notificationService =
                new NotificationService();

        // Step 2: Inject all dependencies into BankService.
        BankService bankService = new BankService(
                validator,
                accountService,
                repository,
                notificationService
        );

        // Step 3: Customer deposit request.
        BigDecimal depositAmount = new BigDecimal("1000");

        String customerEmail = "mahesh@gmail.com";

        // Step 4: Execute the complete deposit workflow.
        bankService.deposit(depositAmount, customerEmail);

        // Step 5: Display the final account balance.
        System.out.println(
                "\nFinal Account Balance: "
                        + accountService.getBalance());
    }
}
