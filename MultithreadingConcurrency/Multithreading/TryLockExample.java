package MultithreadingConcurrency.Multithreading;

import java.util.concurrent.locks.ReentrantLock;

public class TryLockExample {

    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            lock.lock();
            try {
                System.out.println("Thread-1 acquired lock");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } finally {
                lock.unlock();
                System.out.println("Thread-1 released lock");
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(200); // Let Thread-1 acquire the lock
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            if (lock.tryLock()) {
                try {
                    System.out.println("Thread-2 acquired lock");
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println("Thread-2 could not acquire lock");
            }
        });

        t1.start();
        t2.start();
    }
}
