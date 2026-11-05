
package Multithreading;

/*
9. Use wait() and notify()
Core concepts: monitor and condition
Approach: A thread checks a shared condition inside a synchronized block. If the condition is false, it waits; another thread updates the condition and notifies waiting threads.
Remember:
- Call wait(), notify(), or notifyAll() while owning the same object's monitor.
- wait() releases that monitor.
- notify() does not immediately release the monitor.
- Recheck the condition with while.
Interview follow-ups
- Why can spurious wakeups happen?
- What causes IllegalMonitorStateException?
- Why is BlockingQueue usually preferable for producer-consumer?
*/
public class WaitNotifyDemo {

    private static final Object lock = new Object();
    private static boolean messageReady = false;

    public static void main(String[] args) {

        Thread consumer = new Thread(() -> {
            synchronized (lock) {
                while (!messageReady) {
                    try {
                        System.out.println("Consumer: Waiting for message");
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }

                System.out.println("Consumer: Message received");
            }
        });

        Thread producer = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            synchronized (lock) {
                messageReady = true;
                System.out.println("Producer: Message is ready");
                lock.notifyAll();
            }
        });

        consumer.start();
        producer.start();
    }
}
