package MultithreadingConcurrency.Multithreading;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/*
14. Implement a thread-safe cache
Core tool: ConcurrentHashMap

Approach: Use ConcurrentHashMap for concurrent reads and updates. Use computeIfAbsent() when you want to compute and store a missing value.
Remember:
- It does not allow null keys or values.
- If the mapping function returns null or throws, a value is not installed by that computation.
- Thread safety does not provide expiration or freshness automatically.
- Keep mapping functions short and avoid risky side effects.
Interview follow-ups
- Why not use a plain HashMap?
- What does computeIfAbsent() offer over get() followed by put()?
- What if the cached data changes in the database?
- When would you use a cache library with TTL, size limits, and refresh support?
*/
public class ThreadSafeCache {

    private final ConcurrentMap<Integer, String> cache = new ConcurrentHashMap<>();

    public void put(int playerId, String profile) {
        cache.put(playerId, profile);
    }

    public String get(int playerId) {
        return cache.get(playerId);
    }

    public String getOrLoad(int playerId) {
        return cache.computeIfAbsent(playerId, id -> {
            System.out.println("Loading player " + id + " from database");
            return "Player-" + id;
        });
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadSafeCache cache = new ThreadSafeCache();

        Runnable task = () -> System.out.println(Thread.currentThread().getName() + ": " + cache.getOrLoad(101));

        Thread t1 = new Thread(task, "Thread-A");
        Thread t2 = new Thread(task, "Thread-B");

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}