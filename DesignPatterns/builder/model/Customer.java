package DesignPatterns.builder.model;

/*
 * CUSTOMER CLASS - BUILDER DESIGN PATTERN
 *
 * This class represents a customer in our FinFlow application.
 *
 * PROBLEM WITHOUT BUILDER:
 * If a class has many fields, creating objects using constructors
 * becomes difficult because we must remember the order of arguments.
 *
 * BUILDER SOLUTION:
 * We create a separate Builder class that allows us to set object properties using meaningful method names.
 *
 * The final Customer object is created by calling build().
 *
 * IMPORTANT DESIGN DECISIONS:
 *
 * 1. Customer fields are private and final.
 *    This makes the created Customer object immutable.
 *
 * 2. Customer constructor is private.
 *    Other classes cannot directly create Customer objects.
 *
 * 3. Builder is a static nested class.
 *    It can be used to configure and create Customer objects.
 */
public class Customer {

    // Customer data should not be modified after object creation.
    private final int customerId;
    private final String name;
    private final String email;
    private final String mobileNumber;
    private final String address;
    private final String dateOfBirth;
    private final String accountType;
    private final boolean kycVerified;

    /*
     * Private constructor:
     * Prevents direct object creation using new Customer().
     * Only the Builder should create the final Customer object.
     * We copy all values from Builder into Customer.
     */
    private Customer(Builder builder) {

        this.customerId = builder.customerId;
        this.name = builder.name;
        this.email = builder.email;
        this.mobileNumber = builder.mobileNumber;
        this.address = builder.address;
        this.dateOfBirth = builder.dateOfBirth;
        this.accountType = builder.accountType;
        this.kycVerified = builder.kycVerified;
    }

    /*
     * Static nested Builder class:
     *
     * It holds temporary values while the caller configures
     * the Customer object.
     *
     * Unlike Customer, Builder fields are not final because
     * we need to modify them during object construction.
     */
    public static class Builder {

        private final int customerId;
        private final String name;

        // Optional fields with default values.
        private String email;
        private String mobileNumber;
        private String address;
        private String dateOfBirth;
        private String accountType = "SAVINGS";
        private boolean kycVerified;

        /*
         * Builder constructor:
         * Customer ID and name are mandatory in this example.
         * We pass mandatory values here so that the Builder cannot be created without them.
         */
        public Builder(int customerId, String name) {

            this.customerId = customerId;
            this.name = name;
        }

        /*
         * Each method sets one optional property.
         * Returning 'this' allows method chaining.
         * For example: builder.email(...).mobileNumber(...).address(...)
         */
        public Builder email(String email) {

            this.email = email;

            return this;
        }

        public Builder mobileNumber(String mobileNumber) {

            this.mobileNumber = mobileNumber;

            return this;
        }

        public Builder address(String address) {

            this.address = address;

            return this;
        }

        public Builder dateOfBirth(String dateOfBirth) {

            this.dateOfBirth = dateOfBirth;

            return this;
        }

        public Builder accountType(String accountType) {

            this.accountType = accountType;

            return this;
        }

        public Builder kycVerified(boolean kycVerified) {

            this.kycVerified = kycVerified;

            return this;
        }

        /*
         * Final build() method:
         *
         * Once all required and optional values are configured, this method creates and returns the final Customer object.
         * This is the only place where the Customer constructor is called.
         */
        public Customer build() {

            // Basic validation before creating the object.
            if (name == null || name.isEmpty()) {
                throw new IllegalArgumentException(
                        "Customer name is required"
                );
            }

            return new Customer(this);
        }
    }

    /*
     * Getter methods:
     *
     * Customer fields are private, so callers access them
     * through these methods.
     *
     * There are intentionally no setters because this
     * Customer object is immutable.
     */
    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAccountType() {
        return accountType;
    }

    public boolean isKycVerified() {
        return kycVerified;
    }

    /*
     * toString() helps display the Customer object's data
     * in a readable format.
     */
    @Override
    public String toString() {

        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", address='" + address + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", accountType='" + accountType + '\'' +
                ", kycVerified=" + kycVerified +
                '}';
    }
}
