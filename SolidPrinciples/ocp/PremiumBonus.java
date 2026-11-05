package SolidPrinciples.ocp;

/**
 * RESPONSIBILITY: Calculates bonus for premium accounts.
 * Bonus rate: 5%
 */
public class PremiumBonus implements BonusCalculator {

    @Override
    public double calculate(double amount) {

        return amount * 0.05;
    }
}
