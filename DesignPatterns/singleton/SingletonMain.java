package DesignPatterns.singleton;

/*
 * SINGLETON DESIGN PATTERN - MAIN CLASS
 *
 * This class demonstrates all five common Singleton approaches.
 *
 * 1. Eager Initialization
 * 2. Lazy Initialization
 * 3. Synchronized Method
 * 4. Double-Checked Locking
 * 5. Enum Singleton
 *
 * For each approach, we request the instance twice
 * and compare the references using ==.
 *
 * If the result is true, both references point to
 * the same object.
 */
public class SingletonMain {

    public static void main(String[] args) {

        /*
         * 1. EAGER INITIALIZATION
         *
         * Object is already created during class initialization.
         * Both calls return the same object.
         */
        System.out.println("\n===== EAGER SINGLETON =====");

        EagerSingleton eager1 = EagerSingleton.getInstance();
        EagerSingleton eager2 = EagerSingleton.getInstance();

        eager1.displayMessage();

        System.out.println("Same instance: " + (eager1 == eager2));


        /*
         * 2. LAZY INITIALIZATION
         *
         * Object is created only during the first getInstance()
         * call.
         *
         * This basic implementation is not thread-safe.
         */
        System.out.println("\n===== LAZY SINGLETON =====");

        LazySingleton lazy1 = LazySingleton.getInstance();
        LazySingleton lazy2 = LazySingleton.getInstance();

        lazy1.displayMessage();

        System.out.println("Same instance: " + (lazy1 == lazy2));


        /*
         * 3. SYNCHRONIZED SINGLETON
         *
         * Only one thread can execute getInstance()
         * at a time.
         */
        System.out.println("\n===== SYNCHRONIZED SINGLETON =====");

        SynchronizedSingleton sync1 =
                SynchronizedSingleton.getInstance();

        SynchronizedSingleton sync2 =
                SynchronizedSingleton.getInstance();

        sync1.displayMessage();

        System.out.println("Same instance: " + (sync1 == sync2));


        /*
         * 4. DOUBLE-CHECKED LOCKING
         *
         * Uses volatile and synchronized to ensure
         * thread-safe lazy initialization.
         *
         * Synchronization is avoided after initialization.
         */
        System.out.println("\n===== DOUBLE-CHECKED SINGLETON =====");

        DoubleCheckedSingleton double1 =
                DoubleCheckedSingleton.getInstance();

        DoubleCheckedSingleton double2 =
                DoubleCheckedSingleton.getInstance();

        double1.displayMessage();

        System.out.println("Same instance: " + (double1 == double2));


        /*
         * 5. ENUM SINGLETON
         *
         * Access the single instance using the enum constant.
         * No getInstance() method is required.
         */
        System.out.println("\n===== ENUM SINGLETON =====");

        EnumSingleton enum1 = EnumSingleton.INSTANCE;
        EnumSingleton enum2 = EnumSingleton.INSTANCE;

        enum1.displayMessage();

        System.out.println("Same instance: " + (enum1 == enum2));
    }
}
