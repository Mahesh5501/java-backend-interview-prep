
package CircuitBreaker;



/*
 * CIRCUIT BREAKER - REVISION NOTES
 *
 * Purpose: Protect our backend from repeatedly calling an unhealthy service.
 *
 * 1. CLOSED:
 *    Requests are allowed. Count consecutive failures.
 *    If failures reach the limit (e.g., 3), open the circuit.
 *
 * 2. OPEN:
 *    Reject requests immediately without calling the service.
 *    Wait for the cooldown period (e.g., 5 seconds).
 *
 * 3. HALF_OPEN:
 *    After cooldown, allow a test request to check recovery.
 *    If the test succeeds, go to CLOSED and reset failures.
 *    If the test fails, go back to OPEN and restart cooldown.
 *
 * Example:
 *    Failure 1 -> CLOSED
 *    Failure 2 -> CLOSED
 *    Failure 3 -> OPEN
 *    Wait 5 seconds -> HALF_OPEN on the next request
 *    Test succeeds -> CLOSED
 *    Test fails -> OPEN again
 *
 * Important:
 * - Circuit Breaker protects against failing dependencies.
 * - Rate Limiter controls how many requests are allowed.
 * - A timeout limits how long we wait for a slow service.
 */

public class CircuitBreaker {

    String state = "CLOSED";

    int failureCount = 0;
    int failureLimit = 3;

    long openedAt = 0;
    long waitTime = 5000; // 5 seconds

    public void callService(boolean serviceFails) {
        // Step 1: Check if circuit is OPEN
        if (state.equals("OPEN")) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - openedAt < waitTime) {
                System.out.println("Request rejected. Circuit is OPEN.");
                return;
            }
            state = "HALF_OPEN";
            System.out.println("Testing service recovery...");
        }

        // Step 2: Check whether service fails
        if (serviceFails) {
            System.out.println("Service failed.");
            if (state.equals("HALF_OPEN")) {
                state = "OPEN";
                openedAt = System.currentTimeMillis();
                System.out.println("Recovery failed. Circuit OPEN again.");
            } else {
                failureCount++;
                System.out.println("Failure count: " + failureCount);
                if (failureCount >= failureLimit) {
                    state = "OPEN";
                    openedAt = System.currentTimeMillis();
                    System.out.println("Failure limit reached. Circuit OPEN.");
                }
            }

        } else {
            // Step 3: Service succeeded
            System.out.println("Service succeeded.");
            if (state.equals("HALF_OPEN")) {
                state = "CLOSED";
                failureCount = 0;
                System.out.println("Recovery successful. Circuit CLOSED.");
            } else {
                failureCount = 0;
            }
        }

        System.out.println("Current state: " + state);
        System.out.println("-------------------------");
    }

    public static void main(String[] args) throws InterruptedException {

        CircuitBreaker breaker = new CircuitBreaker();

        breaker.callService(true);
        breaker.callService(true);
        breaker.callService(true);

        // Circuit is now OPEN
        breaker.callService(false);

        // Wait for 5 seconds
        Thread.sleep(5100);

        // Try the service again; this test succeeds
        breaker.callService(false);
    }
}
