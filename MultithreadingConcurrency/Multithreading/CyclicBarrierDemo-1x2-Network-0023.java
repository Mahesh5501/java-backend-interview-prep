
package MultithreadingConcurrency.Multithreading;

import java.util.concurrent.CyclicBarrier;

/*
11. Use CyclicBarrier
Core tool: reusable meeting point
Approach: Create new CyclicBarrier(3). Each of three participating threads calls await() at a phase boundary. They proceed once all three have arrived.
Remember: The barrier can be reused for another phase after a successful trip. Interruption, timeout, or a failed participant can break the barrier and affect waiting participants.
Interview follow-ups
- How does a barrier differ from a latch?
- What does BrokenBarrierException mean?
- What is the barrier action?
- What happens if one participant never arrives?
*/
public class CyclicBarrierDemo {

    public static void main(String[] args) {

        CyclicBarrier barrier = new CyclicBarrier(3, () ->
                System.out.println("All workers reached the barrier!")
        );

        for (int i = 1; i <= 3; i++) {
            final int workerId = i;

            new Thread(() -> {
                try {
                    System.out.println("Worker " + workerId + " processing data");
                    Thread.sleep(workerId * 1000);
                    System.out.println("Worker " + workerId + " reached the barrier");
                    barrier.await();
                    System.out.println("Worker " + workerId + " continues");
                } catch (Exception e) {
                    System.out.println("Worker " + workerId + " could not continue");
                }
            }).start();
        }
    }
}
