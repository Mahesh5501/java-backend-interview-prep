package SolidPrinciples.srp;

import java.math.BigDecimal;

/**
 * RESPONSIBILITY: Handles transaction persistence.
 * SRP: In a real application, this class would communicate with a database using JPA or JDBC.
 * For learning, we simulate database persistence using console output.
 */
public class TransactionRepository {

    /**
     * Saves the deposit transaction.
     *
     * @param amount transaction amount
     */
    public void save(BigDecimal amount) {

        System.out.println(
                "Transaction saved successfully. Amount: "
                        + amount);
    }
}
