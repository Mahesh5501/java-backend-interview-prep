package DesignPatterns.singleton;

/*
 * EAGER INITIALIZATION SINGLETON
 *
 * This approach creates the Singleton object when the JVM
 * loads this class.
 *
 * WHY DO WE USE IT?
 * If we know that the object will definitely be required
 * during application execution, we can create it upfront.
 *
 * HOW DOES IT ENSURE SINGLETON?
 * The instance variable is static, so there is only one
 * instance variable associated with this class.
 *
 * The constructor is private, so other classes cannot
 * create objects using the new keyword.
 */
public class EagerSingleton {

    /*
     * static:
     * The instance belongs to the class rather than
     * individual objects.
     *
     * final:
     * Once assigned, this reference cannot point to
     * another EagerSingleton object.
     *
     * The object is created when the class is initialized.
     */
    private static final EagerSingleton INSTANCE =
            new EagerSingleton();

    /*
     * Private constructor:
     * Prevents other classes from directly creating
     * instances using new EagerSingleton().
     */
    private EagerSingleton() {
        System.out.println("Eager Singleton instance created");
    }

    /*
     * Public static access method:
     * Provides a common access point to the shared instance.
     *
     * Every caller receives the same object.
     */
    public static EagerSingleton getInstance() {
        return INSTANCE;
    }

    /*
     * Sample business method to verify that the Singleton
     * object can be accessed and used.
     */
    public void displayMessage() {
        System.out.println("Using Eager Singleton");
    }
}
