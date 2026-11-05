package Multithreading;

/*
5. Synchronize access to a shared resource
Core tool: synchronized

Approach: Protect the complete check-and-update operation, such as checking a wallet balance and then debiting it, under the same lock.
Remember: Protecting only the update while leaving the check outside the lock can still allow a race condition.
Interview follow-ups
- Synchronized method versus synchronized block?
- Instance lock versus static class lock?
- Does a Java lock protect data across multiple application instances? No.
- How do you protect a real wallet balance? Use suitable database transactions and concurrency
*/
class Wallet {

    private int balance = 1000;

    public void debit(int amount) {
        // Protect the shared balance update.
        synchronized (this) {
            if (balance >= amount) {
                System.out.println(Thread.currentThread().getName() + " is processing debit");
                balance = balance - amount;
                System.out.println("Remaining balance: " + balance);
            } else {
                System.out.println(Thread.currentThread().getName() + ": Insufficient balance");
            }
        }
    }

    public synchronized int getBalance() {
        return balance;
    }
}

public class SharedResourceDemo {
    public static void main(String[] args) throws InterruptedException {
        Wallet wallet = new Wallet();
        Thread t1 = new Thread(() -> wallet.debit(700), "Request-1");
        Thread t2 = new Thread(() -> wallet.debit(700), "Request-2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Final balance: " + wallet.getBalance());
    }
}

