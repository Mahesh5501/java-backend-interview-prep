package MultithreadingConcurrency.Multithreading;

import java.util.concurrent.*;

public class ScatterGatherDemo {

    static String getProfile() {
        sleep(200);
        return "Profile: Mahesh";
    }

    static String getWallet() {
        sleep(300);
        return "Wallet: 5000";
    }

    static String getHistory() {
        sleep(500);
        return "Games played: 20";
    }

    static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        try {
            // Scatter: start independent tasks
            CompletableFuture<String> profile = CompletableFuture.supplyAsync(() -> getProfile(), executor);
            CompletableFuture<String> wallet = CompletableFuture.supplyAsync(() -> getWallet(), executor);
            CompletableFuture<String> history =
                    CompletableFuture.supplyAsync(() -> getHistory(), executor)
                            .exceptionally(ex -> {
                        System.out.println("History API failed: " + ex.getMessage());
                        return "History temporarily unavailable";
                    });

            // Gather: combine their results
            CompletableFuture<String> dashboard =
                    profile.thenCombine(wallet, (p, w) -> p + "\n" + w)
                            .thenCombine(history, (result, h) -> result + "\n" + h);

            System.out.println(dashboard.join());
        } finally {
            executor.shutdown();
        }
    }
}