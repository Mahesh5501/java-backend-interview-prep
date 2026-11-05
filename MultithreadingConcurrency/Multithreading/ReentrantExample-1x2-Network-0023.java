
package MultithreadingConcurrency.Multithreading;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantExample {

    private static final ReentrantLock lock = new ReentrantLock();

    public static void method1() {
        lock.lock();
        try {
            System.out.println("Inside method1");
            method2();
        } finally {
            lock.unlock();
        }
    }

    public static void method2() {
        lock.lock();
        try {
            System.out.println("Inside method2");
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        method1();
    }
}
