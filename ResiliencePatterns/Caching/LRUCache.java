package Caching;

import java.util.LinkedHashMap;

/*
 * LRU CACHE - LEAST RECENTLY USED
 *
 * PURPOSE: Store a limited number of items and remove the item that was accessed least recently when the cache is full.
 *
 * HOW IT WORKS:
 * 1. Store key-value pairs in LinkedHashMap.
 * 2. Set accessOrder = true to maintain access order.
 * 3. When get(key) is called, that key becomes the most recently accessed key.
 * 4. When a new item is added and size exceeds capacity:
 *      - Get the first key in the map.
 *      - Remove that key because it is the least recent.
 *
 * EXAMPLE:
 * Capacity = 3
 *
 * Insert A, B, C:
 * Order: A -> B -> C
 *        Oldest     Newest
 *
 * Access A:
 * Order: B -> C -> A
 *
 * Insert D:
 * Order: B -> C -> A -> D
 * Size = 4, but capacity = 3.
 *
 * Remove B because it is the least recently accessed.
 * Final order: C -> A -> D
 *
 * IMPORTANT CODE:
 * new LinkedHashMap<>(16, 0.75f, true)
 *
 * The final true enables access-order tracking.
 *
 * cache.keySet().iterator().next() - Returns the first key in the current order.
 * cache.remove(oldestKey) - Removes the least recently accessed key.
 * COMPLEXITY: Average O(1) for get and put operations.
 * INTERVIEW NOTE: LRU tracks when an item was last accessed, not how many times it was accessed.

 * This simple implementation is not thread-safe.
 */

public class LRUCache {

    int capacity = 3;

    LinkedHashMap<String, String> cache = new LinkedHashMap<>(16, 0.75f, true);

    public void put(String key, String value) {
        cache.put(key, value);

        // Remove the least recently used item if full.
        if (cache.size() > capacity) {
            String oldestKey = cache.keySet().iterator().next();
            cache.remove(oldestKey);
        }
    }

    public String get(String key) {
        return cache.get(key);
    }

    public static void main(String[] args) {
        LRUCache lru = new LRUCache();
        lru.put("A", "Apple");
        lru.put("B", "Banana");
        lru.put("C", "Cherry");
        lru.get("A"); // A becomes recently used
        lru.put("D", "Dates"); // B gets removed
        System.out.println(lru.cache);
    }
}