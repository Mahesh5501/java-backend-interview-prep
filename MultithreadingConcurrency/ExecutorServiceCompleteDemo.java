
package MultithreadingConcurrency;

import java.util.concurrent.*;


/*
=========================================================
EXECUTORS, RUNNABLE, CALLABLE AND FUTURE - REVISION NOTES
=========================================================

1. ExecutorService
   - Manages a pool of reusable worker threads.
   - Example: Executors.newFixedThreadPool(2).
   - Allows multiple tasks to execute using managed threads.

2. Runnable
   - Represents a task that returns no result.
   - Method: void run().
   - Can be submitted using execute() or submit().

3. Callable<V>
   - Represents a task that returns a result of type V.
   - Method: V call().
   - Can throw checked exceptions.
   - Submit it using submit().

4. execute() vs submit()
   - execute(Runnable): submits a task; returns no Future.
   - submit(Runnable): returns Future<?>; get() returns null
     after successful completion.
   - submit(Callable<V>): returns Future<V> for its result.

5. Future<V>
   - Represents the result of an asynchronous task.
   - get(): waits for completion and returns the result.
   - get(timeout, unit): waits only up to the given timeout.
   - isDone(): checks whether the task completed, failed, or was cancelled; does not wait.
   - cancel(true): attempts cancellation and requests interruption if the task is running.
   - isCancelled(): checks whether cancellation succeeded.

6. Future exception handling
   - ExecutionException: get() reports an exception thrown by a submitted task.
   - getCause(): retrieves the original task exception.
   - CancellationException: get() on a cancelled task.
   - TimeoutException: timed get() exceeds its wait limit.
   - InterruptedException: the current thread is interrupted while waiting; restore the interrupt status if not propagating the exception.

7. Thread-pool sizing
   - CPU-intensive tasks: start around available CPU cores and benchmark.
   - I/O-intensive tasks: more threads may help while tasks wait for databases or network responses.
   - Consider queue capacity, rejection policies, timeouts, and resource limits in production.

8. shutdown()
   - Rejects new tasks.
   - Allows previously submitted tasks to finish.
   - Does not itself wait for termination.

9. shutdownNow()
   - Attempts to interrupt running tasks.
   - Returns tasks that have not started.
   - Does not guarantee immediate termination.

10. awaitTermination(timeout, unit)
    - Waits for executor termination up to the given timeout.
    - Returns true if terminated; otherwise false.

11. Runnable/Callable vs creating Threads
    - Runnable and Callable define the work.
    - ExecutorService manages execution.
    - A thread pool avoids creating a new thread for every task.

12. Important interview points
    - Task execution order is not guaranteed.
    - get() can block the calling thread.
    - A timeout does not automatically cancel a task.
    - Interruption is cooperative, not forced thread termination.
    - Always manage executor shutdown.
    - ConcurrentHashMap makes individual supported operations
      thread-safe, not an entire multi-step business workflow.
    - submit() captures task exceptions; inspect the Future
      to discover failures.
    - Avoid blocking immediately after every submission if
      you want tasks to execute concurrently.

=========================================================
*/

public class ExecutorServiceCompleteDemo {
    public static void main(String[] args) throws Exception {

        // =================================================
        // 1. CREATE A THREAD POOL
        // =================================================

        ExecutorService executor = Executors.newFixedThreadPool(2);
        System.out.println("Main thread started");

        // =================================================
        // 2. RUNNABLE + execute()
        // =================================================

        Runnable statusTask = () -> {
            System.out.println("Checking transfer status on " + Thread.currentThread().getName());
        };
        executor.execute(statusTask);  // Submits the task. No Future is returned.

        // =================================================
        // 3. RUNNABLE + submit()
        // =================================================

        Runnable notificationTask = () -> {
            System.out.println("Sending transfer notification");
        };

        Future<?> runnableFuture = executor.submit(notificationTask);
        runnableFuture.get();  // Wait until this task finishes.
        System.out.println("Runnable result: " + runnableFuture.get());  // Successful Runnable tasks return null through Future.

        // =================================================
        // 4. CALLABLE + FUTURE
        // =================================================

        Callable<Integer> feeTask = () -> {
            System.out.println("Calculating transfer fee");
            return 50;
        };
        Future<Integer> feeFuture = executor.submit(feeTask);
        System.out.println("Fee calculation finished? " + feeFuture.isDone()); // isDone() checks completion without waiting.
        Integer fee = feeFuture.get();  // get() waits if the task has not finished yet.
        System.out.println("Transfer fee: " + fee);
        System.out.println("Fee calculation finished? " + feeFuture.isDone());


        // =================================================
        // 5. FUTURE WITH A TIMEOUT
        // =================================================

        Future<String> reportFuture = executor.submit(() -> {
            Thread.sleep(500);
            return "Transfer report ready";
        });
        try {
            System.out.println(reportFuture.get(100, TimeUnit.MILLISECONDS)); // Wait at most 100 milliseconds for this result.
        } catch (TimeoutException e) {
            System.out.println("Report is taking longer than expected");
            System.out.println("Report may still finish: " + !reportFuture.isDone()); // Timeout does NOT automatically cancel the task.
        }
        System.out.println(reportFuture.get());  // We can still wait for the report to finish.


        // =================================================
        // 6. HANDLE A TASK EXCEPTION
        // =================================================

        Future<Integer> failedFuture = executor.submit(() -> {
            throw new IllegalStateException("Transfer failed");
        });

        try {
            Integer result = failedFuture.get();
            System.out.println("Result: " + result);
        } catch (ExecutionException e) {
            System.out.println("Task failed: " + e.getCause().getMessage()); // get() wraps the task's exception in ExecutionException.
        }


        // =================================================
        // 7. CANCEL A TASK
        // =================================================

        CountDownLatch taskStarted = new CountDownLatch(1);

        Future<String> cancellableFuture = executor.submit(() -> {
            taskStarted.countDown();

            try {
                Thread.sleep(10000);
                return "Task completed";
            } catch (InterruptedException e) {
                System.out.println("Task received interruption");
                Thread.currentThread().interrupt();
                throw e;
            }
        });
        taskStarted.await(); // Ensure the task has started before trying to cancel it.

        boolean cancelled = cancellableFuture.cancel(true);

        System.out.println("Cancellation requested: " + cancelled);
        System.out.println("Was task cancelled? " + cancellableFuture.isCancelled());
        try {
            cancellableFuture.get();
        } catch (CancellationException e) {
            System.out.println("Cannot retrieve a cancelled task's result");
        }


        // =================================================
        // 8. GRACEFUL SHUTDOWN + AWAIT TERMINATION
        // =================================================

        executor.shutdown(); // Stop accepting new tasks; already submitted tasks can finish.
        try {
            // Wait up to 5 seconds for termination.
            boolean terminated = executor.awaitTermination(5, TimeUnit.SECONDS);
            if (terminated) {
                System.out.println("Executor terminated gracefully");
            } else {
                System.out.println("Executor did not terminate in time");
                executor.shutdownNow(); // Request interruption of running tasks and return tasks that have not started.
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }


        // =================================================
        // 9. DEMONSTRATE shutdownNow() SEPARATELY
        // =================================================

        ExecutorService secondExecutor = Executors.newSingleThreadExecutor();
        CountDownLatch secondTaskStarted = new CountDownLatch(1);
        secondExecutor.submit(() -> {
            secondTaskStarted.countDown();
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                System.out.println("Second task was interrupted");
                Thread.currentThread().interrupt();
            }
        });
        secondTaskStarted.await();
        secondExecutor.shutdownNow(); // Requests interruption of running tasks.

        try {
            secondExecutor.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Demo completed");
    }
}
