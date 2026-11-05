package SolidPrinciples.srp;

/**
 * RESPONSIBILITY: Handles customer notifications.
 * SRP: If we change from email to SMS or another provider, notification-related implementation changes belong here or in a dedicated notification implementation.
 */
public class NotificationService {

    /**
     * Sends a deposit confirmation to the customer.
     *
     * @param email customer email address
     */
    public void sendDepositNotification(String email) {

        System.out.println(
                "Deposit confirmation sent to: " + email);
    }
}