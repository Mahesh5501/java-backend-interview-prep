package Multithreading;


/*
4. Implement a thread-safe counter
Core tools: synchronized, AtomicInteger

Approach: Protect count++ with synchronization or use AtomicInteger.incrementAndGet().
Remember: count++ is a read-modify-write operation. volatile int makes reads/writes visible but does not make the increment atomic.
Interview follow-ups
- When would you choose AtomicInteger over synchronized?
- What is CAS (compare-and-set)?
- What does join() do?
- Can an atomic counter protect a multi-step business transaction? Not by itself.
*/
class Counter {
    private int count = 0;
    // Only one thread can execute this method at a time.
    public synchronized void increment() {
        count++;
    }
    public synchronized int getCount() {
        return count;
    }
}

public class ThreadSafeCounterDemo {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        };
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        Thread t3 = new Thread(task);

        t1.start();
        t2.start();
        t3.start();
        // Wait for all threads to finish.
        t1.join();
        t2.join();
        t3.join();
        System.out.println(counter.getCount());
    }
}

