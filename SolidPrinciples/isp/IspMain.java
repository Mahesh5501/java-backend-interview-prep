package SolidPrinciples.isp;

import java.math.BigDecimal;

/*
ISP = Focused Interfaces
- Avoid large interfaces with unrelated methods.
- Create smaller interfaces for specific capabilities.
- Classes should implement only what they actually support.
- Avoid unnecessary empty methods and unsupported operations.
Remember: Don't force classes to implement unnecessary methods.

 * ISP (Interface Segregation Principle):
 *
 * A class should not be forced to implement methods
 * that it does not need or support.
 *
 * Instead of creating one large interface with many
 * methods, create smaller, specific interfaces based
 * on the capabilities required by different classes.
 *
 * Example: A Fixed Deposit account should not be forced
 * to implement withdrawal functionality if it does not
 * support withdrawals.
 */
public class IspMain {

    public static void main(String[] args) {

        // Create a savings account.
        SavingsAccount savingsAccount =
                new SavingsAccount(new BigDecimal("5000"));

        // Create a fixed deposit account.
        FixedDepositAccount fixedDepositAccount =
                new FixedDepositAccount(new BigDecimal("10000"));

        // Both accounts support deposits.
        savingsAccount.deposit(new BigDecimal("1000"));
        fixedDepositAccount.deposit(new BigDecimal("2000"));

        // Only SavingsAccount supports withdrawals.
        savingsAccount.withdraw(new BigDecimal("500"));

        // Both accounts support balance enquiry.
        System.out.println(
                "Savings Balance: " + savingsAccount.getBalance()
        );

        System.out.println(
                "Fixed Deposit Balance: " + fixedDepositAccount.getBalance()
        );

        // Only FixedDepositAccount implements interest calculation.
        System.out.println(
                "Fixed Deposit Interest: "
                        + fixedDepositAccount.calculateInterest()
        );
    }
}
