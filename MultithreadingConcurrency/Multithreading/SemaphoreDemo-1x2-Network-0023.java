
package MultithreadingConcurrency.Multithreading;

import java.util.concurrent.Semaphore;

/*
12. Use Semaphore
Core tool: permits
Approach: new Semaphore(3) allows at most three threads to hold permits at once.
Semaphore semaphore = new Semaphore(3);

semaphore.acquire();
try {
    callExternalService();
} finally {
    semaphore.release();
}


Remember: Acquire before entering the limited section and release in finally. Track whether acquisition succeeded before releasing if interruption can happen during acquisition.
Interview follow-ups
- Why use a semaphore for a connection or API concurrency limit?
- How does Semaphore(1) differ from ReentrantLock?
- What happens if a permit is never released?
- Does a semaphore limit requests per second? No.
*/
public class SemaphoreDemo {

    private static final Semaphore semaphore = new Semaphore(3);

    public static void callExternalService(int requestId) {
        try {
            semaphore.acquire();
            try {
                System.out.println("Request " + requestId + " acquired a permit");
                // Simulate an external API call
                Thread.sleep(2000);
                System.out.println("Request " + requestId + " completed");
            } finally {
                semaphore.release();
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Request " + requestId + " was interrupted");
        }
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 6; i++) {
            final int requestId = i;
            new Thread(() -> callExternalService(requestId)).start();
        }
    }
}
