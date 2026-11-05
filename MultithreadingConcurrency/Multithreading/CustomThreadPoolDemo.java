
package Multithreading;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/*
8. Implement a custom thread pool conceptually
Core concepts: workers, task queue, shutdown
Approach: Maintain a fixed number of worker threads that repeatedly take tasks from a shared queue and execute them.
Remember: A custom teaching implementation must handle shutdown, rejected tasks, worker interruption, task exceptions, and queue capacity correctly. In production, normally use ExecutorService.
Interview follow-ups
- Why not create a new thread for every task?
- What is the risk of an unbounded queue? Memory exhaustion.
- shutdown() versus shutdownNow()?
- What is the difference between a task queue and a worker thread?
*/
public class CustomThreadPoolDemo {

    static class CustomThreadPool {
        private final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();

        private final Thread[] workers;
        private volatile boolean shutdown = false;

        public CustomThreadPool(int poolSize) {
            if (poolSize <= 0) {
                throw new IllegalArgumentException("Pool size must be positive");
            }

            workers = new Thread[poolSize];

            for (int i = 0; i < poolSize; i++) {
                workers[i] = new Thread(() -> {
                    while (true) {
                        try {
                            if (shutdown && queue.isEmpty()) {
                                break;
                            }
                            Runnable task = queue.poll(100, TimeUnit.MILLISECONDS);
                            if (task != null) {
                                task.run();
                            }
                        } catch (InterruptedException e) {
                            if (shutdown) {
                                break;
                            }
                        } catch (RuntimeException e) {
                            System.out.println("Task failed: " + e.getMessage());
                        }
                    }
                }, "Worker-" + (i + 1));
                workers[i].start();
            }
        }

        public void submit(Runnable task) {
            if (shutdown) {
                throw new IllegalStateException("Pool is shut down");
            }
            queue.offer(task);
        }

        public void shutdown() throws InterruptedException {
            shutdown = true;
            for (Thread worker : workers) {
                worker.join();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CustomThreadPool pool = new CustomThreadPool(2);

        for (int i = 1; i <= 5; i++) {
            final int taskId = i;

            pool.submit(() -> {
                System.out.println(Thread.currentThread().getName() + " executing Task-" + taskId);
            });
        }
        pool.shutdown();
        System.out.println("All tasks completed");
    }
}
