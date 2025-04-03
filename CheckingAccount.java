import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a checking account.
 * Implements the Account interface and provides additional check-related functionality.
 */
public class CheckingAccount implements Account{
    private BigDecimal balance;
    private static final BigDecimal INTEREST_RATE = new BigDecimal("0.0");
    private List<Integer> processedChecks;

    /**
     * Constructs a new checking account with zero balance.
     */
    public CheckingAccount() {
        this.balance = BigDecimal.ZERO;
        processedChecks = new ArrayList<>();
    }

    /**
     * Constructs a new checking account with specified initial balance.
     * 
     * @param balance the initial balance
     * @throws IllegalArgumentException if balance is negative
     */
    public CheckingAccount(double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.balance = new BigDecimal(String.valueOf(balance));
        processedChecks = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deposit(double amount) throws IllegalArgumentException{
        if (amount < 0) {
            throw new IllegalArgumentException("Deposit amount cannot be negative");
        }
        if (hasMoreThanTwoDecimalPlaces(amount)) {
            throw new IllegalArgumentException("Deposit amount cannot have more than two decimal places");
        }

        this.balance = balance.add(new BigDecimal(String.valueOf(amount)));
        System.out.println("Deposited $" + amount + " into Checking Account");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean withdraw(double amount) throws IllegalArgumentException{
        if (amount < 0) {
            throw new IllegalArgumentException("Withdraw amount cannot be negative");
        }

        if (hasMoreThanTwoDecimalPlaces(amount)) {
            throw new IllegalArgumentException("Deposit amount cannot have more than two decimal places");
        }

        BigDecimal withdrawAmount = new BigDecimal(String.valueOf(amount));
        if (withdrawAmount.compareTo(balance) > 0) {
            System.out.println("Insufficient funds");
            return false;
        } else {
            this.balance = balance.subtract(withdrawAmount);
            System.out.println("Withdrawn $" + amount + " from Checking Account");
            return true;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void calculateInterest() {
        BigDecimal interest = balance.multiply(INTEREST_RATE);
        this.balance = balance.add(interest);
        System.out.println("Added interest of $" + interest.setScale(2, RoundingMode.HALF_UP) + " to Checking Account with interest rate " + INTEREST_RATE.multiply(new BigDecimal("100")) + "%");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal getBalance() {
        return balance.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Deposits a check into the account.
     * 
     * @param amount the amount on the check
     * @param checkNumber the unique identifier for the check
     * @throws IllegalArgumentException if amount is negative or has more than two decimal places
     */
    public void depositCheck(double amount, int checkNumber) throws IllegalArgumentException {
        if (amount < 0) {
            throw new IllegalArgumentException("Deposit amount cannot be negative");
        }

        if (hasMoreThanTwoDecimalPlaces(amount)) {
            throw new IllegalArgumentException("Deposit amount cannot have more than two decimal places");
        }

        if (processedChecks.contains(checkNumber)) {
            System.out.println("This deposit check #" + checkNumber + " has already been processed.");
            return;
        }

        processedChecks.add(checkNumber);
        this.balance = balance.add(new BigDecimal(String.valueOf(amount)));
        System.out.println("Deposited check #" + checkNumber + " for $" + amount + " into Checking Account");
    }

    /**
     * Cashes a check from the account.
     * 
     * @param amount the amount on the check
     * @param checkNumber the unique identifier for the check
     * @return true if check was cashed successfully, false otherwise
     * @throws IllegalArgumentException if amount is negative or has more than two decimal places
     */
    public boolean cashCheck(double amount, int checkNumber) throws IllegalArgumentException{
        if (amount < 0) {
            throw new IllegalArgumentException("Withdraw amount cannot be negative");
        }

        if (hasMoreThanTwoDecimalPlaces(amount)) {
            throw new IllegalArgumentException("Deposit amount cannot have more than two decimal places");
        }

        if (processedChecks.contains(checkNumber)) {
            System.out.println("This deposit check #" + checkNumber + " has already been processed.");
            return false;
        }

        processedChecks.add(checkNumber);
        BigDecimal withdrawAmount = new BigDecimal(String.valueOf(amount));
        if (withdrawAmount.compareTo(balance) > 0) {
            System.out.println("Insufficient funds");
            return false;
        } else {
            this.balance = balance.subtract(withdrawAmount);
            System.out.println("Cashed check #" + checkNumber + " for $" + amount + " from Checking Account");
            return true;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasMoreThanTwoDecimalPlaces(double amount) {
        double shifted = amount * 100;
        return Math.abs(shifted - Math.round(shifted)) > 0;
    }
}
