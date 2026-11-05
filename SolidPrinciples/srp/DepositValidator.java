package SolidPrinciples.srp;

import java.math.BigDecimal;

/**
 * RESPONSIBILITY: Validates whether a deposit amount is acceptable.
 * SRP: This class only handles deposit validation.
 * If validation rules change, this is the class we modify.
 */
public class DepositValidator {

    /**
     * Checks whether the deposit amount is valid.
     *
     * @param amount amount customer wants to deposit
     * @throws IllegalArgumentException if amount is null or non-positive
     */
    public void validate(BigDecimal amount) {

        // A null amount is not allowed.
        if (amount == null) {
            throw new IllegalArgumentException(
                    "Deposit amount cannot be null");
        }

        // Deposits must be greater than zero.
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Deposit amount must be greater than zero");
        }

        System.out.println("Deposit validation successful.");
    }
}
