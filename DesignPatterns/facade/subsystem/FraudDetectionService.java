package DesignPatterns.facade.subsystem;

import java.math.BigDecimal;

public class FraudDetectionService {

    public void checkTransfer(String fromAccount,
                              String toAccount,
                              BigDecimal amount) {

        // Simplified fraud check for demonstration.
        System.out.println("Fraud check completed");
    }
}