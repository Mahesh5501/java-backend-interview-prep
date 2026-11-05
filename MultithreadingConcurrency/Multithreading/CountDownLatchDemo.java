package MultithreadingConcurrency.Multithreading;

import java.util.concurrent.CountDownLatch;


/*
10. Use CountDownLatch
Core tool: one-time countdown
Approach: Initialize a latch with the number of events you need to wait for. Each worker calls countDown() when its event is complete; the coordinator calls await().
CountDownLatch latch = new CountDownLatch(3);

// Each of three workers:
try {
    initializeComponent();
} finally {
    latch.countDown();
}

// Coordinator:
latch.await();


This is a pattern fragment: the workers and coordinator must share the same latch, and the await() call must be handled appropriately.
Remember: A latch is one-shot. If a worker never counts down, the coordinator can wait indefinitely unless it uses a timeout or interruption.
Interview follow-ups
- CountDownLatch versus Thread.join()?
- CountDownLatch versus CyclicBarrier?
- Does timeout automatically cancel unfinished workers? No.
- How should task failures be communicated to the coordinator?
*/
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);

        Thread config = new Thread(() -> {
            System.out.println("Loading configuration");
            latch.countDown();
        });

        Thread database = new Thread(() -> {
            System.out.println("Connecting to database");
            latch.countDown();
        });

        Thread metadata = new Thread(() -> {
            System.out.println("Loading game metadata");
            latch.countDown();
        });

        config.start();
        database.start();
        metadata.start();

        latch.await();

        System.out.println("All initialization tasks completed");
        System.out.println("Application is ready");


    }
}
