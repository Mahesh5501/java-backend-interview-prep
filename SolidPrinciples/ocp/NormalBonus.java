package SolidPrinciples.ocp;

/**
 * RESPONSIBILITY: Calculates bonus for normal accounts.
 * Bonus rate: 1%
 */
public class NormalBonus implements BonusCalculator {

    @Override
    public double calculate(double amount) {

        return amount * 0.01;
    }
}
