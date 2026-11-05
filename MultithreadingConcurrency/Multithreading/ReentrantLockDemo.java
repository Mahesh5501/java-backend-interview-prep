package MultithreadingConcurrency.Multithreading;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {

    static class BankAccount {
        private int balance = 1000;
        private final ReentrantLock lock = new ReentrantLock();

        public void withdraw(int amount) {
            lock.lock(); // Acquire the lock

            try {
                if (balance >= amount) {
                    balance = balance - amount;
                    System.out.println(
                            Thread.currentThread().getName()
                                    + " withdrew " + amount
                                    + ", balance = " + balance
                    );
                } else {
                    System.out.println("Insufficient balance");
                }
            } finally {
                lock.unlock(); // Always release the lock
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BankAccount account = new BankAccount();

        Thread t1 = new Thread(() -> account.withdraw(700), "Thread-1");
        Thread t2 = new Thread(() -> account.withdraw(700), "Thread-2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Final balance: " + 300);
    }
}
