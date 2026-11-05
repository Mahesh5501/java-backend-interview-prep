package SolidPrinciples.ocp;

/**
 * RESPONSIBILITY: Calculates bonus for employee accounts.
 * Bonus rate: 10%
 */
public class EmployeeBonus implements BonusCalculator {

    @Override
    public double calculate(double amount) {

        return amount * 0.10;
    }
}
