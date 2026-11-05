package SolidPrinciples.lsp;

import java.math.BigDecimal;

/*
 * FixedDepositAccount supports deposits.
 *
 * It does not implement WithdrawableAccount because
 * withdrawals are not supported before maturity.
 *
 * Therefore, we do not need to override withdraw()
 * or throw UnsupportedOperationException.
 */
public class FixedDepositAccount extends Account {

    public FixedDepositAccount(BigDecimal initialBalance) {
        super(initialBalance);
    }
}
