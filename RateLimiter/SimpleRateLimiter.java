
package RateLimiter;

/*
 * RATE LIMITER - FIXED WINDOW COUNTER
 *
 * PURPOSE: Control how many requests a client can make in a time period.
 * EXAMPLE: Allow only 3 requests every 10 seconds.

 * HOW IT WORKS:
 * 1. Store the window start time and request count.
 * 2. When a request arrives, check whether 10 seconds have passed.
 * 3. If the window expired:
 *      - Reset the request count to 0.
 *      - Start a new window.
 * 4. If requestCount < requestLimit:
 *      - Increase the count.
 *      - Allow the request.
 * 5. Otherwise, reject the request.

 * EXAMPLE FLOW:
 * Request 1 -> ALLOWED (count = 1)
 * Request 2 -> ALLOWED (count = 2)
 * Request 3 -> ALLOWED (count = 3)
 * Request 4 -> REJECTED (limit reached)
 * New window -> Reset count and allow requests again.

 * WHY SYNCHRONIZED?
 * Multiple threads may check the counter simultaneously. synchronized allows only one thread at a time to check the limit and update the counter on the same object.

 * EDGE CASES:
 * 1. Limit reached -> Reject additional requests.
 * 2. Window expires -> Reset the counter.
 * 3. Concurrent requests -> synchronized protects this object.
 * 4. Multiple backend servers -> Each server has its own counter. Use shared storage such as Redis with atomic updates to enforce a common limit across servers.

 * LIMITATION:
 * Fixed windows can allow bursts at window boundaries.
 * Example: 3 requests just before the window ends and 3 more immediately after the next window begins.

 * INTERVIEW POINTS:
 * - Rate Limiter controls request frequency.
 * - HTTP 429 means Too Many Requests.
 * - synchronized protects one JVM object, not multiple servers.
 * - Token Bucket allows controlled bursts.
 * - Sliding Window reduces fixed-window boundary problems.
 */

public class SimpleRateLimiter {

    private int requestCount = 0;
    private final int requestLimit = 3;

    private long windowStartTime = System.currentTimeMillis();
    private final long windowDuration = 10_000;

    // Only one thread can check and update the counter at a time.
    public synchronized boolean allowRequest() {
        long currentTime = System.currentTimeMillis();

        // Start a new window if the old window has expired.
        if (currentTime - windowStartTime >= windowDuration) {
            requestCount = 0;
            windowStartTime = currentTime;
            System.out.println("New window started.");
        }

        // Allow only up to the configured limit.
        if (requestCount < requestLimit) {
            requestCount++;
            System.out.println("Request ALLOWED. Count: " + requestCount);
            return true;
        }

        System.out.println("Request REJECTED. Limit reached.");
        return false;
    }

    public static void main(String[] args) {

        SimpleRateLimiter limiter = new SimpleRateLimiter();

        for (int i = 1; i <= 5; i++) {
            System.out.println("Request " + i);
            limiter.allowRequest();
        }
    }
}
