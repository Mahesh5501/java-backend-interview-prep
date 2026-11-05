package MultithreadingConcurrency.Multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class ConcurrencyLimitDemo {

    private static final Semaphore permits = new Semaphore(3);

    private static void callWalletApi(int requestId) {
        boolean acquired = false;

        try {
            permits.acquire();
            acquired = true;

            System.out.println("Request " + requestId + " started");
            Thread.sleep(1000); // Simulate API call
            System.out.println("Request " + requestId + " completed");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            if (acquired) {
                permits.release();
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        try {
            for (int i = 1; i <= 10; i++) {
                final int id = i;
                executor.submit(() -> callWalletApi(id));
            }
        } finally {
            executor.shutdown();
        }
    }
}
