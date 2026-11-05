package DesignPatterns.singleton;

/*
 * ENUM SINGLETON
 *
 * Java provides Enum as a simple and robust way to implement
 * Singleton.
 *
 * WHY DO WE USE IT?
 *
 * Normally, implementing Singleton requires us to handle
 * object creation, thread safety, and serialization concerns.
 *
 * Enum handles these concerns through Java's built-in
 * enum initialization and serialization mechanisms.
 *
 * Enum constants are initialized once when the enum class
 * is initialized.
 */
public enum EnumSingleton {

    /*
     * This enum constant represents the single instance.
     *
     * Java ensures that this constant is created only once.
     */
    INSTANCE;

    /*
     * Business method:
     * We can define normal methods inside an enum.
     */
    public void displayMessage() {
        System.out.println("Using Enum Singleton");
    }
}
