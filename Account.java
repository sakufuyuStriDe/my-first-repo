import java.math.BigDecimal;

/**
 * Interface representing a bank account.
 * Defines methods for basic banking operations.
 */
public interface Account {
    /**
     * Deposits an amount into the account.
     * 
     * @param amount the amount to deposit
     * @throws IllegalArgumentException if amount is negative or has more than two decimal places
     */
    void deposit(double amount);

    /**
     * Withdraws an amount from the account.
     * 
     * @param amount the amount to withdraw
     * @return true if withdrawal was successful, false otherwise
     * @throws IllegalArgumentException if amount is negative or has more than two decimal places
     */
    boolean withdraw(double amount);

    /**
     * Calculates and adds interest to the account based on its current balance.
     */
    void calculateInterest();

    /**
     * Returns the current account balance.
     * 
     * @return the current balance of the account
     */
    BigDecimal getBalance();

    /**
     * Checks if an amount has more than two decimal places.
     * 
     * @param amount the amount to check
     * @return true if the amount has more than two decimal places, false otherwise
     */
    boolean hasMoreThanTwoDecimalPlaces(double amount);
}
