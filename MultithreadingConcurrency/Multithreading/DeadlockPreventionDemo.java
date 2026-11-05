package MultithreadingConcurrency.Multithreading;

/*
7. Detect or prevent deadlock
Core tools: lock ordering, tryLock()

Approach 1: Acquire multiple locks in the same global order in every code path.
Approach 2: Use timed ReentrantLock.tryLock() and release already-acquired locks if the next acquisition fails.
Remember: Always release locks in finally. Timed retries can still cause starvation or livelock if poorly designed.
Interview follow-ups
- How does consistent lock ordering break circular wait?
- How can you inspect deadlocked threads using ThreadMXBean or jstack?
- What is the difference between deadlock and starvation?
- Why can a timeout help without guaranteeing fairness?
*/
public class DeadlockPreventionDemo {

    private static final Object lockA = new Object();
    private static final Object lockB = new Object();

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> transfer("Thread-1"));
        Thread t2 = new Thread(() -> transfer("Thread-2"));

        t1.start();
        t2.start();
    }

    private static void transfer(String threadName) {
        // Always acquire A before B.
        synchronized (lockA) {
            System.out.println(threadName + " acquired lockA");

            synchronized (lockB) {
                System.out.println(threadName + " acquired lockB");
                System.out.println(threadName + " completed transfer");
            }
        }
    }
}
