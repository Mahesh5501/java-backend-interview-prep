package DesignPatterns.singleton;

/*
 * LAZY INITIALIZATION SINGLETON
 *
 * This approach creates the Singleton object only when
 * getInstance() is called for the first time.
 *
 * WHY DO WE USE IT?
 * Sometimes an object may not be required immediately
 * when the application starts.
 *
 * Instead of creating it unnecessarily, we create it
 * only when it is actually requested.
 *
 * IMPORTANT:
 * This basic implementation is NOT thread-safe.
 */
public class LazySingleton {

    /*
     * Initially, no object is created.
     *
     * The reference is null until the first call
     * to getInstance().
     */
    private static LazySingleton instance;

    /*
     * Private constructor prevents external object creation.
     */
    private LazySingleton() {
        System.out.println("Lazy Singleton instance created");
    }

    /*
     * Returns the Singleton object.
     *
     * First call:
     * instance is null, so a new object is created.
     *
     * Subsequent calls:
     * instance already contains an object, so the same
     * object is returned.
     *
     * THREAD-SAFETY PROBLEM:
     * If two threads execute this method simultaneously,
     * both may observe null and create separate objects.
     */
    public static LazySingleton getInstance() {

        if (instance == null) {

            instance = new LazySingleton();

        }

        return instance;
    }

    public void displayMessage() {
        System.out.println("Using Lazy Singleton");
    }
}
