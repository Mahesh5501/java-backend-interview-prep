
package RateLimiter;


/*
 * TOKEN BUCKET RATE LIMITER - REVISION NOTES
 *
 * PURPOSE:
 * Limit request frequency while allowing small bursts.
 *
 * IMPORTANT VARIABLES:
 * 1. capacity       -> Maximum tokens the bucket can hold.
 * 2. tokens         -> Tokens currently available.
 * 3. refillRate     -> Tokens added per second.
 * 4. lastRefillTime -> Time of the previous refill calculation.
 *
 * ALGORITHM:
 * 1. Calculate elapsed time since the previous refill.
 * 2. Add tokens based on elapsed time.
 * 3. Never allow tokens to exceed capacity.
 * 4. If tokens >= 1:
 *      - Consume one token.
 *      - Allow the request.
 * 5. Otherwise:
 *      - Reject the request.
 *
 * EXAMPLE:
 * Capacity = 3 tokens
 * Refill rate = 1 token/second
 *
 * Initially: 3 tokens
 * Request 1 -> Allowed; 2 remain.
 * Request 2 -> Allowed; 1 remains.
 * Request 3 -> Allowed; 0 remain.
 * Request 4 -> Rejected.
 * Wait 1 second -> Approximately 1 token refilled.
 * Next request -> Allowed.
 *
 * WHY SYNCHRONIZED?
 * Prevents concurrent threads from consuming the same
 * available token on the same object.
 *
 * INTERVIEW COMPARISON:
 * Fixed Window -> Resets a request counter per time window.
 * Token Bucket -> Consumes tokens and refills them over time.
 *
 * DISTRIBUTED SYSTEM:
 * Multiple application servers need shared, atomic
 * token updates, for example through Redis.
 */

public class RateLimiterTokenBucket {

    int tokens = 3;

    public boolean allowRequest() {

        if (tokens > 0) {
            tokens--;
            System.out.println("Request allowed");
            System.out.println("Tokens left: " + tokens);
            return true;
        }
        System.out.println("Request rejected. No tokens left.");
        return false;
    }

    public static void main(String[] args) {

        RateLimiterTokenBucket bucket = new RateLimiterTokenBucket();

        bucket.allowRequest();
        bucket.allowRequest();
        bucket.allowRequest();
        bucket.allowRequest();
    }
}
