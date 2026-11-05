package DesignPatterns.facade.subsystem;

import java.math.BigDecimal;

public class NotificationService {

    public void sendNotification(String fromAccount,
                                 String toAccount,
                                 BigDecimal amount) {

        System.out.println("Transfer notification sent");
    }
}