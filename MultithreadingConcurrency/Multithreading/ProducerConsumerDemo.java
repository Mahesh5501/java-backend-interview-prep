package MultithreadingConcurrency.Multithreading;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


/*
3. Implement producer-consumer
Core tool: BlockingQueue

Approach: Producer uses put(); consumer uses take(). A bounded ArrayBlockingQueue provides backpressure when full.
Remember:
- put() blocks when the queue is full.
- take() blocks when the queue is empty.
- Prefer BlockingQueue over hand-written wait()/notify() for ordinary producer-consumer problems.
Interview follow-ups
- Why use a bounded queue?
- How do you stop consumers gracefully?
- What changes with multiple producers or consumers?
- What happens if the producer fails before sending the poison pill?
*/
public class ProducerConsumerDemo {
    public static void main(String[] args) {
        // Queue can hold only 2 items.
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(2);

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    String event = "Event-" + i;
                    queue.put(event); // Wait if queue is full.
                    System.out.println("Produced: " + event);
                }
                queue.put("STOP"); // Tell consumer to finish.
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Producer");

        Thread consumer = new Thread(() -> {
            try {
                while (true) {
                    String event = queue.take(); // Wait if queue is empty.
                    if ("STOP".equals(event)) {
                        break;
                    }
                    System.out.println("Consumed: " + event);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Consumer");

        producer.start();
        consumer.start();
    }
}

