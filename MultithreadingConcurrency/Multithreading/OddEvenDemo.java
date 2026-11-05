package MultithreadingConcurrency.Multithreading;


/*
1. Print odd and even numbers using two threads
Core tools: synchronized, wait(), notifyAll()

Approach: Share one counter between the odd and even threads. Each waits while the counter belongs to the other thread, prints its number, increments the counter, and notifies the other thread.
Remember:
- Use while, not if, around wait().
- wait() releases the monitor; sleep() does not.
- Notify after changing the shared state.
Interview follow-ups :-
- Why must both threads share the same lock?
- Why is notifyAll() safer than notify() in this pattern?
- What happens if one thread is interrupted?
- Is volatile alone sufficient? No — it does not provide the required atomic coordination.
*
*/

class OddEvenPrinter {
    // Shared state: both threads access the same number.
    // volatile is NOT needed here because synchronized provides the required visibility and mutual exclusion.
    private int number = 1;
    private final int limit = 10;

    // Prints odd numbers: 1, 3, 5, 7, 9.
    // synchronized: Only one thread at a time can execute a synchronized method on this same OddEvenPrinter object.
    public synchronized void printOdd() throws InterruptedException {
        while (number <= limit) {
            /*
             * If number is even, it is the even thread's turn.
             *
             * wait():
             * 1. Releases this object's monitor lock.
             * 2. Puts the current thread into the waiting state.
             * 3. The thread waits until notified or otherwise awakened.
             *
             * Use while, not if, because after waking up the thread
             * must check the condition again.
             */
            while (number <= limit && number % 2 == 0) {
                wait();
            }
            if (number <= limit) { //Recheck the limit because another thread may have changed number while this thread was waiting.
                System.out.println(Thread.currentThread().getName() + ": " + number); // Print the current odd number.
                number++; // Move to the next number.
                notifyAll(); //Wake all threads waiting on this object's monitor. The even thread can now check whether it is its turn.
            }
        }
    }

     //Prints even numbers: 2, 4, 6, 8, 10.
    public synchronized void printEven() throws InterruptedException {
        while (number <= limit) {
            while (number <= limit && number % 2 != 0) { // If number is odd, wait for the odd thread.
                wait();
            }
            if (number <= limit) {
                System.out.println(Thread.currentThread().getName() + ": " + number);
                number++;
                notifyAll();// Wake the waiting odd thread.
            }
        }
    }
}

public class OddEvenDemo {

    public static void main(String[] args) {

        // Both threads MUST share the same printer object.
        // Otherwise, they would have separate numbers and separate locks.
        OddEvenPrinter printer = new OddEvenPrinter();

        Thread oddThread = new Thread(() -> {
            try {
                printer.printOdd();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore the interrupt status after catching the exception.
            }
        }, "OddThread");

        Thread evenThread = new Thread(() -> {
            try {
                printer.printEven();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "EvenThread");

        // start() schedules each thread to execute independently. Calling run() directly would not start a new thread.
        oddThread.start();
        evenThread.start();
    }
}

