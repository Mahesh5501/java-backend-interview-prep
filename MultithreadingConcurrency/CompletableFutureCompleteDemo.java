package MultithreadingConcurrency;

import java.util.concurrent.*;


/*
================================================================
COMPLETABLEFUTURE - COMPLETE REVISION NOTES (JAVA 8)
================================================================

1. WHAT IS CompletableFuture?
   - It represents the result of an asynchronous computation.
   - Asynchronous means a task can run without making the calling thread wait immediately for its result.
   - It supports chaining tasks, combining results, and handling exceptions.
   - Unlike a basic Future, it provides methods to connect dependent tasks without manually calling get() between them.

---------------------------------------------------------------
2. runAsync() - TASK WITHOUT A RESULT
---------------------------------------------------------------
Example:
    CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            System.out.println("Sending notification");
        });

Explanation:
   - Runs an action asynchronously.
   - Use it when you do not need a returned value.
   - The result type is Void because there is no useful result.
   - By default, it uses the common ForkJoinPool.
   - join() can be used to wait for completion.

Real example: Send a transfer notification.

---------------------------------------------------------------
3. supplyAsync() - TASK WITH A RESULT
---------------------------------------------------------------
Example: CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 50);

Explanation:
   - Runs a task asynchronously and returns a value.
   - Integer represents the expected result type.
   - By default, it uses the common ForkJoinPool.
   - Use it when a later task needs the returned value.

Real example: Calculate a transfer fee and return 50.

---------------------------------------------------------------
4. thenApply() - TRANSFORM THE RESULT
---------------------------------------------------------------
Example: future.thenApply(fee -> fee + 10);

Explanation:
   - Receives the previous task's result.
   - Transforms it and returns a new result.
   - Here, 50 becomes 60.
   - It does not automatically create a new asynchronous task.
   - A non-Async continuation may run on the thread that completes the previous future or another thread calling the continuation, depending on timing.

Real example: Add a service charge to the transfer fee.

---------------------------------------------------------------
5. thenAccept() - CONSUME THE RESULT
---------------------------------------------------------------
Example: future.thenAccept(fee -> System.out.println(fee));

Explanation:
   - Receives the previous result.
   - Performs an action with that value.
   - Does not return a useful value.
   - The resulting future is CompletableFuture<Void>.

Real example: Print or log the final transfer fee.

---------------------------------------------------------------
6. thenRun() - NEXT ACTION WITHOUT THE RESULT
---------------------------------------------------------------
Example: future.thenRun(() -> System.out.println("Finished"));

Explanation:
   - Runs an action after the previous future completes normally.
   - Does not receive the previous result.
   - Does not return a useful result.

Difference:
   thenAccept() receives the previous result.
   thenRun() does not receive the previous result.

---------------------------------------------------------------
7. thenApplyAsync() - ASYNCHRONOUS TRANSFORMATION
---------------------------------------------------------------
Example: future.thenApplyAsync(fee -> fee * 2);

Explanation:
   - Transforms the previous result.
   - The continuation is scheduled asynchronously.
   - Without a custom executor, it normally uses the common ForkJoinPool.
   - It does not guarantee that every continuation uses a different physical thread.

Difference:
   thenApply() performs a transformation without explicitly requesting asynchronous scheduling.
   thenApplyAsync() requests asynchronous scheduling.

---------------------------------------------------------------
8. CUSTOM EXECUTOR
---------------------------------------------------------------
Example:
    ExecutorService executor = Executors.newFixedThreadPool(2);
    CompletableFuture.supplyAsync(() -> 100, executor);

Explanation:
   - Allows us to control which executor runs the task.
   - A fixed pool of 2 has at most 2 worker threads executing tasks at the same time.
   - Useful for separating workloads and controlling resources.
   - Always manage the executor's lifecycle.
   - shutdown() rejects new tasks but lets submitted tasks finish.

Production note: Consider bounded queues, rejection policies, timeouts, and resource limits where appropriate.

---------------------------------------------------------------
9. thenCompose() - CHAIN DEPENDENT ASYNC TASKS
---------------------------------------------------------------
Example: CompletableFuture<String> receipt = transactionFuture.thenCompose(id ->
            CompletableFuture.supplyAsync(
                () -> "Receipt for " + id
            )
        );

Explanation:
   - Used when the next operation itself returns a CompletableFuture.
   - Starts or connects the next asynchronous operation after receiving the previous result.
   - Flattens the result instead of nesting futures.

Real example: First obtain a transaction ID. Then asynchronously fetch its receipt.

Difference:
   thenApply() transforms a value.
   thenCompose() chains another asynchronous operation.

---------------------------------------------------------------
10. thenCombine() - COMBINE TWO INDEPENDENT RESULTS
---------------------------------------------------------------
Example:
    feeFuture.thenCombine(
        taxFuture,
        (fee, tax) -> fee + tax
    );

Explanation:
   - Both input futures can run independently.
   - The combining function runs when both complete normally.
   - It receives both results and produces one result.

Real example: Fee = 50; tax = 5; total = 55.
Use it when two tasks do not depend on each other's results.

---------------------------------------------------------------
11. allOf() - WAIT FOR ALL FUTURES
---------------------------------------------------------------
Example:
    CompletableFuture.allOf(task1, task2, task3).join();

Explanation:
   - Completes when all supplied futures complete.
   - Its result type is CompletableFuture<Void>.
   - It does not automatically collect each task's result.
   - Retrieve results from the original futures afterwards.
   - If a supplied future fails, allOf() completes exceptionally after all supplied futures have completed.

Real example: Wait for fee, customer, and exchange-rate lookups.

---------------------------------------------------------------
12. anyOf() - FIRST COMPLETED FUTURE
---------------------------------------------------------------
Example: CompletableFuture.anyOf(serverA, serverB);

Explanation:
   - Completes when any supplied future completes.
   - Its result type is CompletableFuture<Object>.
   - The result comes from the first completed future.
   - First completed does not necessarily mean first successful.
   - If that first completion is exceptional, anyOf() can also complete exceptionally.

Real example: Race two read-only service requests and use the first result.

---------------------------------------------------------------
13. exceptionally() - RECOVER FROM A FAILURE
---------------------------------------------------------------
Example:
    CompletableFuture<Integer> future =
        CompletableFuture.<Integer>supplyAsync(() -> {
            throw new RuntimeException("Service failed");
        }).exceptionally(error -> 0);

Explanation:
   - Runs the recovery function if the previous stage fails.
   - Returns a fallback value of the expected result type.
   - Here, the fallback is 0.
   - The recovered future completes normally if the recovery function succeeds.

Important:
   The explicit <Integer> tells Java the expected type when
   the lambda only throws and provides no return value.

Java version:
   exceptionally() is available in Java 8.
   exceptionallyAsync() was introduced in Java 12.

Real example:
   Use a defined fallback when a non-critical lookup fails.
   Do not silently convert critical payment failures into successful-looking results.

---------------------------------------------------------------
14. handle() - PROCESS SUCCESS OR FAILURE
---------------------------------------------------------------
Example:
    future.handle((result, error) -> {
        if (error != null) {
            return -1;
        }
        return result;
    });

Explanation:
   - Runs for normal or exceptional completion.
   - Receives both the result and the error.
   - Returns a new result.
   - Can recover from a failure or transform a successful value.

Difference:
   exceptionally() focuses on failure recovery.
   handle() processes both success and failure.

---------------------------------------------------------------
15. whenComplete() - OBSERVE THE OUTCOME
---------------------------------------------------------------
Example:
    future.whenComplete((result, error) -> {
        if (error == null) {
            System.out.println(result);
        } else {
            System.out.println("Failed");
        }
    });

Explanation:
   - Receives the result and/or error for observation.
   - Commonly used for logging or metrics.
   - Normally preserves the original result or failure.
   - If the callback itself throws, the resulting stage can complete exceptionally.

Difference:
   handle() transforms the result.
   whenComplete() is mainly for observing the outcome.

---------------------------------------------------------------
16. get() VS join()
---------------------------------------------------------------
Example:
    future.get();
    future.join();

Explanation:
   - Both wait for the result.
   - get() throws checked InterruptedException and ExecutionException.
   - join() throws unchecked CompletionException when the computation fails exceptionally.
   - Use get() when checked exception handling is appropriate.
   - Use join() when unchecked completion handling is suitable.

Important: Both can block the calling thread.

---------------------------------------------------------------
17. get(timeout, unit) - TIMED WAIT
---------------------------------------------------------------
Example: future.get(100, TimeUnit.MILLISECONDS);

Explanation:
   - Waits for a result up to the specified duration.
   - Throws TimeoutException if the time limit expires first.
   - A timeout does not automatically cancel the task.
   - The task may still be running.

Real example: Stop waiting indefinitely for a slow service response.

---------------------------------------------------------------
18. isDone(), getNow(), AND cancel()
---------------------------------------------------------------
isDone():
   - Checks whether the future completed, failed, or was cancelled.
   - Does not wait for completion.

getNow(defaultValue):
   - Returns the result if already completed normally.
   - Otherwise returns the supplied default value immediately.
   - If already completed exceptionally, it throws an unchecked completion exception.

cancel(true):
   - Attempts to cancel the future.
   - isCancelled() checks whether it was cancelled.
   - CompletableFuture cancellation does not reliably interrupt the underlying computation.

Important: Cancellation is not the same as guaranteed task termination.

---------------------------------------------------------------
19. complete() - MANUALLY SUPPLY A RESULT
---------------------------------------------------------------
Example: CompletableFuture<String> future = new CompletableFuture<>();
    future.complete("Success");

Explanation:
   - Creates a future that can be completed manually.
   - complete(value) attempts to set its normal result.
   - Returns true if this call completes the future.
   - Returns false if it was already completed.

Real example: Complete a future when an external callback supplies a result.

---------------------------------------------------------------
20. completeExceptionally() - MANUALLY SUPPLY A FAILURE
---------------------------------------------------------------
Example: future.completeExceptionally(new RuntimeException("Transfer rejected"));

Explanation:
   - Completes the future exceptionally.
   - A later get() or join() reports the failure.
   - It does not mean a payment was rolled back automatically.

Real example: An external asynchronous operation reports a failure.

---------------------------------------------------------------
21. SYNCHRONOUS VS ASYNCHRONOUS CONTINUATIONS
---------------------------------------------------------------
Synchronous-style methods: thenApply(), thenAccept(), thenRun()
Async variants: thenApplyAsync(), thenAcceptAsync(), thenRunAsync()

Explanation:
   - Async variants request asynchronous scheduling.
   - Without an explicit executor, they normally use the common ForkJoinPool.
   - A custom executor can be supplied to Async variants.
   - Non-Async continuations are not guaranteed to run on the main thread or on a particular thread.

---------------------------------------------------------------
22. IMPORTANT BACKEND AND INTERVIEW WARNINGS
---------------------------------------------------------------
   - Starting an async task does not mean it has finished.
   - Calling join() immediately can remove much of the benefit of asynchronous execution.
   - Independent tasks can run concurrently; dependent tasks should be chained in the correct order.
   - A timeout does not cancel an operation automatically.
   - CompletableFuture alone does not provide transaction safety, idempotency, or distributed consistency.
   - Use appropriate executors for blocking I/O workloads.
   - Handle errors explicitly and choose fallbacks carefully.
   - For payments, ensure failures do not cause duplicate transfers or misleading success responses.

================================================================
QUICK SUMMARY
================================================================
runAsync()       -> Run an action with no result.
supplyAsync()    -> Run a task that returns a result.
thenApply()      -> Transform a result.
thenAccept()     -> Consume a result.
thenRun()        -> Run the next action without the result.
thenCompose()    -> Chain dependent asynchronous work.
thenCombine()    -> Combine two independent results.
allOf()          -> Wait for all futures.
anyOf()          -> First completed future.
exceptionally()  -> Recover from failure.
handle()         -> Process success or failure.
whenComplete()   -> Observe the outcome.
get()/join()     -> Wait for a result.
complete()       -> Manually set a result.
cancel()         -> Attempt cancellation.

================================================================
*/

public class CompletableFutureCompleteDemo {

    public static void main(String[] args) throws Exception {

        // =====================================================
        // 1. runAsync() - RUN A TASK THAT RETURNS NO RESULT
        // =====================================================

        CompletableFuture<Void> notificationFuture = CompletableFuture.runAsync(() -> {
                    System.out.println("Sending transfer notification");
                });
        notificationFuture.join(); // join() waits until the task completes.


        // =====================================================
        // 2. supplyAsync() - RUN A TASK THAT RETURNS A RESULT
        // =====================================================

        CompletableFuture<Integer> feeFuture = CompletableFuture.supplyAsync(() -> {
                    System.out.println("Calculating transfer fee");
                    return 50;
                });
        System.out.println("Fee: " + feeFuture.join());


        // =====================================================
        // 3. thenApply() - TRANSFORM A RESULT
        // =====================================================

        CompletableFuture<Integer> finalFeeFuture = feeFuture.thenApply(fee -> fee + 10);
        System.out.println("Final fee: " + finalFeeFuture.join());


        // =====================================================
        // 4. thenAccept() - CONSUME A RESULT, RETURN NOTHING
        // =====================================================

        CompletableFuture<Void> printFuture = finalFeeFuture.thenAccept(finalFee -> System.out.println("Fee to charge: " + finalFee));
        printFuture.join();


        // =====================================================
        // 5. thenRun() - RUN NEXT ACTION WITHOUT INPUT
        // =====================================================

        CompletableFuture<Void> nextAction = finalFeeFuture.thenRun(() -> System.out.println("Fee processing finished"));
        nextAction.join();


        // =====================================================
        // 6. thenApplyAsync() - TRANSFORM ASYNCHRONOUSLY
        // =====================================================

        CompletableFuture<Integer> asyncFee = feeFuture.thenApplyAsync(fee -> {
                    System.out.println("Applying async calculation");
                    return fee * 2;
                });
        System.out.println("Doubled fee: " + asyncFee.join());

        // =====================================================
        // 7. CUSTOM EXECUTOR
        // =====================================================

        ExecutorService executor = Executors.newFixedThreadPool(2);

        try {
            CompletableFuture<Integer> customFuture = CompletableFuture.supplyAsync(() -> {
                        System.out.println("Running on: " + Thread.currentThread().getName());
                        return 100;
                    }, executor);
            System.out.println("Custom result: " + customFuture.join());
        } finally {
            executor.shutdown();
        }

        // =====================================================
        // 8. thenCompose() - CHAIN DEPENDENT ASYNC TASKS
        // =====================================================

        CompletableFuture<String> transferFuture = CompletableFuture.supplyAsync(() -> "TXN101");

        CompletableFuture<String> receiptFuture = transferFuture.thenCompose(transactionId -> CompletableFuture.supplyAsync(() -> "Receipt for " + transactionId));
        System.out.println(receiptFuture.join());

        // =====================================================
        // 9. thenCombine() - COMBINE TWO INDEPENDENT RESULTS
        // =====================================================

        CompletableFuture<Integer> fee = CompletableFuture.supplyAsync(() -> 50);
        CompletableFuture<Integer> tax = CompletableFuture.supplyAsync(() -> 5);
        CompletableFuture<Integer> total = fee.thenCombine(tax, (feeAmount, taxAmount) -> feeAmount + taxAmount);
        System.out.println("Total charge: " + total.join());

        // =====================================================
        // 10. allOf() - WAIT FOR ALL TASKS
        // =====================================================

        CompletableFuture<Integer> task1 = CompletableFuture.supplyAsync(() -> 100);
        CompletableFuture<Integer> task2 = CompletableFuture.supplyAsync(() -> 200);
        CompletableFuture<Integer> task3 = CompletableFuture.supplyAsync(() -> 300);
        CompletableFuture<Void> allTasks = CompletableFuture.allOf(task1, task2, task3);
        allTasks.join();
        System.out.println("All results: " + task1.join() + ", " + task2.join() + ", " + task3.join());


        // =====================================================
        // 11. anyOf() - USE THE FIRST COMPLETED TASK'S RESULT
        // =====================================================

        CompletableFuture<String> serverA = CompletableFuture.supplyAsync(() -> {
                    sleep(200);
                    return "Response from server A";
                });

        CompletableFuture<String> serverB = CompletableFuture.supplyAsync(() -> {
                    sleep(50);
                    return "Response from server B";
                });

        CompletableFuture<Object> firstResponse = CompletableFuture.anyOf(serverA, serverB);
        System.out.println("First response: " + firstResponse.join());


        // =====================================================
        // 12. exceptionally() - RECOVER FROM AN EXCEPTION
        // =====================================================

        CompletableFuture<Integer> failedTask = CompletableFuture.supplyAsync(() -> {
                    throw new RuntimeException("Fee service unavailable");
                });

        CompletableFuture<Integer> recoveredTask = failedTask.exceptionally(error -> {
                    System.out.println("Error: " + error.getMessage());
                    return 0;
                });
        System.out.println("Fallback fee: " + recoveredTask.join());


        // =====================================================
        // 13. handle() - PROCESS SUCCESS OR FAILURE
        // =====================================================

        CompletableFuture<Integer> handledTask = CompletableFuture.supplyAsync(() -> 100 / 0)
                        .handle((result, error) -> {
                            if (error != null) {
                                return -1;
                            }
                            return result;
                        });
        System.out.println("Handled result: " + handledTask.join());


        // =====================================================
        // 14. whenComplete() - OBSERVE SUCCESS OR FAILURE
        // =====================================================

        CompletableFuture<Integer> loggedTask = CompletableFuture.supplyAsync(() -> 500)
                        .whenComplete((result, error) -> {
                            if (error == null) {
                                System.out.println("Logging successful result: " + result);
                            } else {
                                System.out.println("Logging task failure");
                            }
                        });
        System.out.println("Logged task result: " + loggedTask.join());


        // =====================================================
        // 15. get() VS join()
        // =====================================================

        CompletableFuture<String> resultFuture = CompletableFuture.supplyAsync(() -> "Transfer successful");
        System.out.println("Using get(): " + resultFuture.get()); // get() throws checked exceptions.
        System.out.println("Using join(): " + resultFuture.join());  // join() throws unchecked CompletionException on failure.

        // =====================================================
        // 16. get(timeout, unit) - WAIT FOR A LIMITED TIME
        // =====================================================

        CompletableFuture<String> slowTask = CompletableFuture.supplyAsync(() -> {
                    sleep(500);
                    return "Slow result";
                });
        try {
            System.out.println(slowTask.get(50, TimeUnit.MILLISECONDS));
        } catch (TimeoutException e) {
            System.out.println("Timed out waiting for result");
        }
        System.out.println("Final slow result: " + slowTask.join());  // Timeout alone does not cancel slowTask.


        // =====================================================
        // 17. isDone(), getNow(), cancel()
        // =====================================================

        CompletableFuture<String> statusFuture = CompletableFuture.supplyAsync(() -> "Completed");
        System.out.println("Done? " + statusFuture.isDone());
        System.out.println("Current value: " + statusFuture.getNow("Not ready yet")); // getNow() returns the value if available; otherwise fallback.

        CompletableFuture<String> cancelFuture = new CompletableFuture<>();
        System.out.println("Cancel result: " + cancelFuture.cancel(true));
        System.out.println("Cancelled? " + cancelFuture.isCancelled());


        // =====================================================
        // 18. complete() - MANUALLY COMPLETE A FUTURE
        // =====================================================

        CompletableFuture<String> manualFuture = new CompletableFuture<>();
        manualFuture.complete("Manual result"); // Supplies the result if the future is not already completed.
        System.out.println(manualFuture.join());


        // =====================================================
        // 19. completeExceptionally() - COMPLETE WITH FAILURE
        // =====================================================

        CompletableFuture<String> manualFailure = new CompletableFuture<>();
        manualFailure.completeExceptionally(new RuntimeException("Transfer rejected"));
        try {
            System.out.println(manualFailure.join());
        } catch (CompletionException e) {
            System.out.println("Manual failure: " + e.getCause().getMessage());
        }


        // =====================================================
        // 20. ASYNC EXCEPTION HANDLING
        // =====================================================

        CompletableFuture<Integer> asyncRecovery = CompletableFuture.<Integer>supplyAsync(() -> {
                    throw new RuntimeException("Service failed");
                }).exceptionally(error -> 0);
        System.out.println("Recovered value: " + asyncRecovery.join());
        System.out.println("Demo completed");
    }

    private static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
