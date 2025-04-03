import java.util.HashMap;
import java.util.Map;

/**
 * Class representing a bank that manages multiple accounts.
 */
public class Bank {
    private Map<String, Account> accounts;

    /**
     * Constructs a new bank with an empty account list.
     */
    public Bank() {
        this.accounts = new HashMap<>();
    }

    /**
     * Creates a new account and adds it to the bank.
     * 
     * @param isChecking true for checking account, false for savings account
     * @param userName the username to associate with the account
     * @return true if account was created successfully, false if username already exists
     */
    public boolean createAccount(boolean isChecking, String userName) {
        if (accounts.containsKey(userName)) {
            return false;
        }

        Account account;

        if (isChecking) {
            account = new CheckingAccount();
        } else {
            account = new SavingsAccount();
        }

        addAccount(userName, account);
        return true;
    }

    /**
     * Adds an account to the bank.
     * 
     * @param userName the username to associate with the account
     * @param account the account to add
     */
    private void addAccount(String userName, Account account) {
        accounts.put(userName, account);
    }

    /**
     * Retrieves an account from the bank by username.
     * 
     * @param userName the username associated with the account
     * @return the account if found, null otherwise
     */
    public Account getAccount(String userName) {
        if (!accounts.containsKey(userName)) {
            System.out.println("Account not found");
            return null;
        }
        return accounts.get(userName);
    }

    
}
