package SolidPrinciples.lsp;

import java.math.BigDecimal;

/*
LSP memory trick
LSP = Safe Substitution
A child should behave as its parent promises. Don't force a child to support operations it cannot perform.

 * LSP (Liskov Substitution Principle):
 *
 * A child class should be replaceable wherever its parent
 * class is expected, without breaking the application's
 * expected behavior.
 *
 * Child classes should correctly follow the behavior
 * and contract defined by their parent class.
 *
 * Example: If a parent Account supports withdrawal,
 * every child class must support withdrawal correctly.
 * If some accounts don't support withdrawal, separate
 * the withdrawal capability into its own interface.
 */
public class LspMain {

    public static void main(String[] args) {

        // Create a savings account with an initial balance.
        SavingsAccount savingsAccount =
                new SavingsAccount(new BigDecimal("5000"));

        // Create a fixed deposit account.
        FixedDepositAccount fixedDepositAccount =
                new FixedDepositAccount(new BigDecimal("10000"));

        // Both account types can perform deposits.
        Account account1 = savingsAccount;
        Account account2 = fixedDepositAccount;

        account1.deposit(new BigDecimal("1000"));
        account2.deposit(new BigDecimal("2000"));

        // Withdrawal service works with withdrawal-capable accounts.
        WithdrawalService withdrawalService = new WithdrawalService();

        withdrawalService.processWithdrawal(
                savingsAccount,
                new BigDecimal("500")
        );

        // Display final balances.
        System.out.println("Savings Balance: " + savingsAccount.getBalance());
        System.out.println("Fixed Deposit Balance: " + fixedDepositAccount.getBalance());
    }
}
