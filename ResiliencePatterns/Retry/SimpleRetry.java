
package Retry;

import java.io.IOException;

/*
 * RETRY MECHANISM WITH EXPONENTIAL BACKOFF
 *
 * PURPOSE:
 * Retry an operation when it fails temporarily, such as
 * when an API call fails because of a temporary network issue.
 *
 * ALGORITHM:
 *
 * 1. Set the maximum number of attempts.
 *    Example: MAX_ATTEMPTS = 4.
 *
 * 2. Call the service.
 *    If it succeeds, return true and stop retrying.
 *
 * 3. If a retryable failure occurs:
 *    - Check whether the maximum attempts are exhausted.
 *    - If exhausted, return false.
 *    - Otherwise, wait before trying again.
 *
 * 4. Use exponential backoff:
 *    - First retry: wait 1 second.
 *    - Second retry: wait 2 seconds.
 *    - Third retry: wait 4 seconds.
 *
 * 5. Apply a maximum delay so the waiting time does not keep increasing indefinitely.
 *
 * 6. If the thread is interrupted while waiting:
 *    - Restore its interrupted status.
 *    - Stop retrying.
 *
 * EXAMPLE:
 * Attempt 1 -> Failed -> Wait 1 second.
 * Attempt 2 -> Failed -> Wait 2 seconds.
 * Attempt 3 -> Successful -> Stop.
 *
 * IMPORTANT PRODUCTION CONSIDERATIONS:
 * - Retry only temporary failures, not every exception.
 * - Add jitter to avoid many clients retrying together.
 * - Configure API timeouts and an overall time budget.
 * - Use idempotency protection for payment operations.
 * - Consider a circuit breaker for unhealthy dependencies.
 *
 * INTERVIEW SUMMARY:
 * Exponential backoff increases the delay between retries
 * to reduce pressure on a temporarily failing service.
 * A maximum attempt limit prevents endless retries.
 */

public class SimpleRetry {

    private static final int MAX_ATTEMPTS = 4;
    private static final long INITIAL_WAIT = 1000;
    private static final long MAX_WAIT = 4000;

    public static boolean executeWithRetry() {

        long waitTime = INITIAL_WAIT;

        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {

            try {
                callService(attempt);
                System.out.println("Request successful.");
                return true;

            } catch (IOException e) {
                System.out.println("Attempt " + attempt + " failed.");
                if (attempt == MAX_ATTEMPTS) {
                    System.out.println("Retries exhausted.");
                    return false;
                }

                try {
                    System.out.println("Retrying after " + waitTime + " ms");
                    Thread.sleep(waitTime);
                } catch (InterruptedException interrupted) {
                    Thread.currentThread().interrupt();
                    System.out.println("Retry interrupted.");
                    return false;
                }
                // Double the delay, but never exceed 4 seconds.
                waitTime = Math.min(waitTime * 2, MAX_WAIT);
            }
        }
        return false;
    }

    // Simulate a service that succeeds on the third attempt.
    private static void callService(int attempt) throws IOException {
        if (attempt < 3) {
            throw new IOException("Temporary service failure");
        }
    }

    public static void main(String[] args) {
        executeWithRetry();
    }
}
