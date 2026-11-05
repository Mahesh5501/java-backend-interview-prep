package MultithreadingConcurrency.Multithreading;


/*
2. Print 1–100 using three threads in order
Core tools: shared counter and turn variable
Approach: Assign turns 0, 1, and 2. Each thread prints only when turn == myTurn, then advances the number and rotates the turn with (turn + 1) % 3.
Remember: Three threads each printing a separate range is a different problem; it does not guarantee alternating output.
Interview follow-ups
- How would you generalize this to \(N\) threads?
- Why is the condition checked in a loop?
- How do you avoid a thread waiting forever after cancellation?
- Is strict ordering the same as fair scheduling? No.
*/
class NumberPrinter {

    private int number = 1;
    private final int limit = 100;
    private int turn = 0; // 0 -> 1 -> 2 -> repeat

    public synchronized void printNumbers(int myTurn) throws InterruptedException {
        while (number <= limit) {
            // Wait until this thread's turn.
            while (number <= limit && turn != myTurn) {
                wait();
            }

            if (number <= limit) {
                System.out.println(Thread.currentThread().getName() + ": " + number);
                number++; // Move to the next number.
                turn = (turn + 1) % 3; // Give the next thread its turn.
                notifyAll(); // Wake waiting threads.
            }
        }
    }
}

public class PrintNumbersUsingMultipleThreads {
    public static void main(String[] args) {
        NumberPrinter printer = new NumberPrinter();
        // Each thread gets a different turn.
        Thread t1 = new Thread(() -> runPrinter(printer, 0), "Thread-1");
        Thread t2 = new Thread(() -> runPrinter(printer, 1), "Thread-2");
        Thread t3 = new Thread(() -> runPrinter(printer, 2), "Thread-3");

        t1.start();
        t2.start();
        t3.start();
    }

    private static void runPrinter(NumberPrinter printer, int turn) {
        try {
            printer.printNumbers(turn);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

