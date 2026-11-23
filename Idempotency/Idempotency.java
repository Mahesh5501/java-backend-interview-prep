package Idempotency;

import java.util.HashMap;
import java.util.Map;


/*
 * IDEMPOTENCY - PAYMENT API
 *
 * PURPOSE:
 * Prevent the same payment request from being processed twice.
 *
 * ALGORITHM:
 * 1. Receive a unique request ID (idempotency key).
 * 2. Check whether the request ID was already processed.
 * 3. If it exists, return the previously stored result.
 * 4. Otherwise, process the payment.
 * 5. Store the result against the request ID.
 *
 * EXAMPLE:
 * PAY101, amount 500 -> Process payment.
 * PAY101, amount 500 -> Return previous result.
 * PAY102, amount 700 -> Process as a new payment.
 *
 * IMPORTANT:
 * A retry of the same request should use the same key.
 *
 * PRODUCTION CONSIDERATIONS:
 * - Store idempotency records durably in a database.
 * - Use a unique constraint or atomic operation to prevent concurrent requests from processing the same payment.
 * - Handle failures between payment processing and saving the result.
 * - Verify that the same key is not reused with different payment details.
 *
 * INTERVIEW SUMMARY:
 * Idempotency makes repeated requests safe by ensuring the same logical operation is not executed twice.
 */


public class Idempotency {

    private final Map<String, String> processedPayments = new HashMap<>();

    public synchronized String makePayment(String requestId, int amount) {

        // If we already processed this request, return its result.
        if (processedPayments.containsKey(requestId)) {
            System.out.println("Duplicate request detected.");
            return processedPayments.get(requestId);
        }

        // Simulate processing the payment.
        String result = "Payment successful: " + amount;

        // Store the result so we don't process this request again.
        processedPayments.put(requestId, result);

        System.out.println("Payment processed.");
        return result;
    }

    public static void main(String[] args) {

        Idempotency payment = new Idempotency();

        System.out.println(payment.makePayment("PAY101", 500));
        System.out.println(payment.makePayment("PAY101", 500));
        System.out.println(payment.makePayment("PAY102", 700));
    }
}
