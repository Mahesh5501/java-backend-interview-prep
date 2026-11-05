package SolidPrinciples.ocp;

/*
 * OCP (Open/Closed Principle):
 * A class should be open for extension but closed for modification.
 * We should be able to add new functionality by creating new implementations without changing existing, working code.
 * Example: Add a new bonus type by implementing BonusCalculator instead of modifying BonusService.
 */


/**
 * MAIN CLASS:
 *
 * Demonstrates the Open/Closed Principle.
 *
 * Each account type has its own bonus implementation.
 * BonusService accepts all of them through one interface.
 */
public class OcpMain {

    public static void main(String[] args) {

        // Create the common bonus service.
        BonusService bonusService = new BonusService();

        // Common deposit amount.
        double depositAmount = 1000;

        // Normal account: 1% bonus.
        BonusCalculator normal = new NormalBonus();

        // Premium account: 5% bonus.
        BonusCalculator premium = new PremiumBonus();

        // Employee account: 10% bonus.
        BonusCalculator employee = new EmployeeBonus();

        // VIP account: 15% bonus.
        BonusCalculator vip = new VipBonus();

        System.out.println("Deposit Amount: " + depositAmount);

        // Calculate bonus using different implementations.
        System.out.println(
                "Normal Bonus: "
                        + bonusService.calculateBonus(
                        normal, depositAmount));

        System.out.println(
                "Premium Bonus: "
                        + bonusService.calculateBonus(
                        premium, depositAmount));

        System.out.println(
                "Employee Bonus: "
                        + bonusService.calculateBonus(
                        employee, depositAmount));

        System.out.println(
                "VIP Bonus: "
                        + bonusService.calculateBonus(
                        vip, depositAmount));
    }
}
