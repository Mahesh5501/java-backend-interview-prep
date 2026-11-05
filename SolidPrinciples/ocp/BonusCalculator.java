package SolidPrinciples.ocp;

/**
 * RESPONSIBILITY: Defines a common contract for all bonus calculations.
 * OCP: New bonus calculation types can be introduced by implementing this interface.
 */
public interface BonusCalculator {

    /**
     * Calculates bonus based on the deposit amount.
     *
     * @param amount deposit amount
     * @return calculated bonus
     */
    double calculate(double amount);
}