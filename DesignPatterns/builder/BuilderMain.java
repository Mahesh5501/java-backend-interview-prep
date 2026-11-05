package DesignPatterns.builder;

import DesignPatterns.builder.model.Customer;

/*
 * BUILDER DESIGN PATTERN
 *
 * Category: Creational Design Pattern.
 *
 * Definition:
 * Constructs complex objects step by step instead of
 * passing many parameters through a constructor.
 *
 * Problem solved:
 * Telescoping Constructor Problem.
 *
 * Main components:
 * 1. Product - final object to be created.
 * 2. Builder - holds values during construction.
 * 3. build() - creates and returns the final object.
 *
 * Important concepts:
 * - Supports method chaining by returning this.
 * - Separates object construction from object representation.
 * - Useful when objects have many optional parameters.
 * - Commonly used to create immutable objects.
 *
 * Java/Spring examples:
 * - Lombok @Builder.
 * - Building DTOs and request objects.
 *
 * Difference:
 * Factory decides which object to create.
 * Builder focuses on how a complex object is constructed.
 */
public class BuilderMain {

    public static void main(String[] args) {

        /*
         * CUSTOMER 1:
         *
         * Mandatory values:
         * customerId and name.
         *
         * Optional values:
         * email, mobile number, address, date of birth,
         * account type, and KYC status.
         *
         * Method chaining makes object creation readable.
         */
        Customer customer1 = new Customer.Builder(101, "Mahesh")
                .email("mahesh@gmail.com")
                .mobileNumber("9876543210")
                .address("Hyderabad")
                .dateOfBirth("1995-01-10")
                .accountType("CURRENT")
                .kycVerified(true)
                .build();

        /*
         * CUSTOMER 2:
         *
         * Only mandatory fields are provided.
         *
         * Optional fields will retain their default values.
         *
         * We don't need to create multiple constructors
         * for different combinations of optional fields.
         */
        Customer customer2 = new Customer.Builder(102, "Rahul")
                .build();

        // Display both created Customer objects.
        System.out.println("Customer 1:");
        System.out.println(customer1);

        System.out.println("\nCustomer 2:");
        System.out.println(customer2);
    }
}
