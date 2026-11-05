package DesignPatterns.facade.subsystem;

public class AccountService {

    public void validateAccounts(String fromAccount, String toAccount) {

        if (fromAccount == null || toAccount == null
                || fromAccount.equals(toAccount)) {
            throw new IllegalArgumentException("Invalid accounts");
        }

        System.out.println("Accounts validated");
    }
}