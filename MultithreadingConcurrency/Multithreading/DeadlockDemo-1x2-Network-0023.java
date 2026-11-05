package MultithreadingConcurrency.Multithreading;

/*
6. Implement a deadlock
Learning demonstration only
Approach: Thread A acquires lock 1 then requests lock 2, while Thread B acquires lock 2 then requests lock 1.
Four necessary conditions: Mutual exclusion, hold-and-wait, no preemption, circular wait.
Interview follow-ups
- How do you identify a deadlock in a thread dump?
- Does Thread.sleep() release a monitor? No.
- Can the JVM automatically resolve every deadlock? No.
Do not run a deliberate deadlock in a production process; it may hang the involved threads indefinitely.
*/
public class DeadlockDemo {

    private static final Object lockA = new Object();
    private static final Object lockB = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (lockA) {
                System.out.println("Thread-1 acquired lockA");
                sleep(); // Give Thread-2 time to acquire lockB.
                System.out.println("Thread-1 waiting for lockB");
                synchronized (lockB) {
                    System.out.println("Thread-1 acquired lockB");
                }
            }
        }, "Thread-1");

        Thread t2 = new Thread(() -> {
            synchronized (lockB) {
                System.out.println("Thread-2 acquired lockB");
                sleep(); // Give Thread-1 time to acquire lockA.
                System.out.println("Thread-2 waiting for lockA");
                synchronized (lockA) {
                    System.out.println("Thread-2 acquired lockA");
                }
            }
        }, "Thread-2");

        t1.start();
        t2.start();
    }

    private static void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

