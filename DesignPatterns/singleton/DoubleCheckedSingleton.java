package DesignPatterns.singleton;

/*
 * DOUBLE-CHECKED LOCKING SINGLETON
 *
 * This approach provides lazy initialization and thread safety
 * while avoiding synchronization on every method call.
 *
 * WHY DO WE USE IT?
 *
 * Synchronized Singleton is thread-safe, but every call
 * acquires the class-level lock.
 *
 * Double-Checked Locking avoids that repeated locking
 * after the instance has been initialized.
 */
public class DoubleCheckedSingleton {

    /*
     * WHY VOLATILE?
     *
     * Multiple threads access this shared instance variable.
     *
     * volatile ensures that changes to this reference
     * are visible to other threads.
     *
     * It also prevents unsafe instruction reordering
     * during object initialization and publication.
     *
     * Without volatile, another thread could potentially
     * observe a reference before the object is fully initialized.
     */
    private static volatile DoubleCheckedSingleton instance;

    /*
     * Prevents direct object creation from outside.
     */
    private DoubleCheckedSingleton() {
        System.out.println("Double-Checked Singleton instance created");
    }

    /*
     * Returns the shared Singleton object.
     */
    public static DoubleCheckedSingleton getInstance() {

        /*
         * FIRST CHECK:
         *
         * If the instance already exists, return it directly.
         *
         * No synchronization is required after initialization.
         */
        if (instance == null) {

            /*
             * Acquire the class-level lock only when the
             * instance has not yet been created.
             *
             * This prevents multiple threads from creating
             * separate instances simultaneously.
             */
            synchronized (DoubleCheckedSingleton.class) {

                /*
                 * SECOND CHECK:
                 *
                 * Another thread might have created the object
                 * while the current thread was waiting for
                 * the synchronization lock.
                 *
                 * Therefore, we must check again before
                 * creating the object.
                 */
                if (instance == null) {

                    instance = new DoubleCheckedSingleton();

                }
            }
        }

        /*
         * All threads receive the same initialized object.
         */
        return instance;
    }

    public void displayMessage() {
        System.out.println("Using Double-Checked Singleton");
    }
}
