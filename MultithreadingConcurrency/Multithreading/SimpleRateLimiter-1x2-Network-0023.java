package MultithreadingConcurrency.Multithreading;

import java.util.ArrayDeque;
import java.util.Deque;


/*
15. Implement rate limiting with concurrency control
Core concepts: concurrency versus rate
Approach:
- Use Semaphore to limit how many operations are in progress.
- Use a time-based rate limiter to limit how many requests may start per second or minute.
- Sometimes you need both.
Remember: A semaphore with five permits does not mean five requests per second. It means at most five permit holders simultaneously.
Interview follow-ups
- What is a token bucket?
- How would you implement a shared limit across multiple service instances?
- What should happen when a limit is exceeded: wait, reject, or return HTTP 429?
- How do you prevent a rate limiter from becoming a bottleneck?
*/
public class SimpleRateLimiter {

    private final int maxRequests;
    private final long windowMillis;
    private final Deque<Long> timestamps = new ArrayDeque<>();

    public SimpleRateLimiter(int maxRequests, long windowMillis) {
        this.maxRequests = maxRequests;
        this.windowMillis = windowMillis;
    }

    public synchronized boolean allowRequest() {
        long now = System.currentTimeMillis();

        while (!timestamps.isEmpty() && now - timestamps.peekFirst() >= windowMillis) {
            timestamps.removeFirst();
        }

        if (timestamps.size() >= maxRequests) {
            return false;
        }

        timestamps.addLast(now);
        return true;
    }

    public static void main(String[] args) {
        SimpleRateLimiter limiter = new SimpleRateLimiter(5, 1000);

        for (int i = 1; i <= 8; i++) {
            System.out.println("Request " + i + ": " + (limiter.allowRequest() ? "Allowed" : "Rejected"));
        }
    }
}
