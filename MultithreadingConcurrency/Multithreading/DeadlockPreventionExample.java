package Multithreading;

import java.util.concurrent.locks.ReentrantLock;

public class DeadlockPreventionExample {

    private static final ReentrantLock lockA = new ReentrantLock();
    private static final ReentrantLock lockB = new ReentrantLock();

    public static void task(String name) {
        lockA.lock();

        try {
            System.out.println(name + " acquired lockA");

            lockB.lock();
            try {
                System.out.println(name + " acquired lockB");
                System.out.println(name + " completed work");
            } finally {
                lockB.unlock();
            }
        } finally {
            lockA.unlock();
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> task("Thread-1"));
        Thread t2 = new Thread(() -> task("Thread-2"));

        t1.start();
        t2.start();
    }
}

