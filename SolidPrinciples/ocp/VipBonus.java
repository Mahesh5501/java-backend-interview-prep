package SolidPrinciples.ocp;

/**
 * RESPONSIBILITY: Calculates bonus for VIP accounts.
 * Bonus rate: 15%
 *
 * This is a NEW implementation.
 * We are extending the existing functionality without modifying BonusService.
 */
public class VipBonus implements BonusCalculator {

    @Override
    public double calculate(double amount) {

        return amount * 0.15;
    }
}
