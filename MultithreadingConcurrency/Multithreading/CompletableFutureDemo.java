
package MultithreadingConcurrency.Multithreading;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/*
13. Use CompletableFuture for parallel API calls
Core tools: supplyAsync, thenCombine

Approach: Start independent requests using supplyAsync() on a suitable executor. Use thenCombine() to combine two results.
Know these methods:
Method	Purpose
supplyAsync()	Run a task that returns a value
runAsync()	Run a task without a result
thenApply()	Transform a result
thenCompose()	Chain another asynchronous operation
thenCombine()	Combine two independent results
allOf()	Wait for all supplied futures to complete
exceptionally()	Recover from an upstream failure
handle()	Process success or failure
join()	Wait for a result; wraps failure in CompletionException
Interview follow-ups
- Why use a dedicated executor for blocking I/O?
- How do you set timeouts?
- Does one failed future automatically cancel all other tasks? No.
- How do you handle partial results without hiding essential failures?
*/
public class CompletableFutureDemo {

    private static String getProfile() {
        sleep();
        return "Player: Mahesh";
    }

    private static String getWallet() {
        sleep();
        return "Wallet: 5000";
    }

    private static String getHistory() {
        sleep();
        return "Games played: 20";
    }

    private static void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        try {
            CompletableFuture<String> profile = CompletableFuture.supplyAsync(() -> getProfile(), executor);
            CompletableFuture<String> wallet = CompletableFuture.supplyAsync(() -> getWallet(), executor);
            CompletableFuture<String> history = CompletableFuture.supplyAsync(() -> getHistory(), executor);
            CompletableFuture<String> dashboard =
                    profile.thenCombine(wallet, (p, w) -> p + "\n" + w)
                            .thenCombine(history, (result, h) -> result + "\n" + h);
            System.out.println(dashboard.join());
        } finally {
            executor.shutdown();
        }
    }
}
