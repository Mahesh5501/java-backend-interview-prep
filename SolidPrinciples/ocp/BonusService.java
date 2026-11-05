package SolidPrinciples.ocp;

/**
 * RESPONSIBILITY: Provides the common bonus calculation operation.
 *
 * OCP: This class works with the BonusCalculator interface.
 * It does not depend on NormalBonus, PremiumBonus, EmployeeBonus or VipBonus directly.
 */
public class BonusService {

    /**
     * Accepts any implementation of BonusCalculator.
     * We don't need if-else or switch statements to identify the account type.
     *
     * @param calculator selected bonus calculation strategy
     * @param amount deposit amount
     * @return calculated bonus
     */
    public double calculateBonus(
            BonusCalculator calculator,
            double amount) {

        return calculator.calculate(amount);
    }
}
