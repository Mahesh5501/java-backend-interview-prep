
package Multithreading;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class LatchTimeoutDemo {

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);

        Thread worker = new Thread(() -> {
            try {
                System.out.println("Worker started");
                // Simulate a long-running operation
                Thread.sleep(10000);
                System.out.println("Worker completed");
            } catch (InterruptedException e) {
                System.out.println("Worker interrupted; stopping");
                Thread.currentThread().interrupt();
            } finally {
                latch.countDown();
            }
        });

        worker.start();

        boolean completed = latch.await(2, TimeUnit.SECONDS);
        if (completed) {
            System.out.println("Task completed in time");
        } else {
            System.out.println("Timeout! Requesting worker to stop");
            worker.interrupt();
        }

        worker.join();
        System.out.println("Main thread finished");
    }
}
