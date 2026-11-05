package DesignPatterns.singleton;

/*
 * SYNCHRONIZED METHOD SINGLETON
 *
 * This approach solves the thread-safety problem
 * present in basic Lazy Initialization.
 *
 * WHY DO WE USE SYNCHRONIZED?
 * Imagine two threads calling getInstance() simultaneously.
 *
 * Without synchronization:
 * Both threads might observe null and create two objects.
 *
 * With synchronization:
 * Only one thread can execute this static synchronized
 * method at a time.
 *
 * Therefore, the second thread waits until the first
 * thread completes its execution.
 */
public class SynchronizedSingleton {

    /*
     * The object is created only when first requested.
     */
    private static SynchronizedSingleton instance;

    /*
     * Prevents direct object creation from outside.
     */
    private SynchronizedSingleton() {
        System.out.println("Synchronized Singleton instance created");
    }

    /*
     * synchronized:
     * Acquires the class-level lock before executing this
     * static method.
     *
     * Only one thread can execute this method at a time.
     *
     * First thread creates the object.
     * Other threads wait and then receive the same object.
     *
     * DISADVANTAGE:
     * Even after the object is created, every call still
     * needs to acquire the synchronization lock.
     */
    public static synchronized SynchronizedSingleton getInstance() {

        if (instance == null) {

            instance = new SynchronizedSingleton();

        }

        return instance;
    }

    public void displayMessage() {
        System.out.println("Using Synchronized Singleton");
    }
}
