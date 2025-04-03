import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Class representing a savings account.
 * Implements the Account interface with a 2% interest rate.
 */
public class SavingsAccount implements Account{
    private BigDecimal balance;
    private static final BigDecimal INTEREST_RATE = new BigDecimal("0.02");

    /**
     * Constructs a new savings account with zero balance.
     */
    public SavingsAccount() {
        this.balance = BigDecimal.ZERO;
    }

    /**
     * Constructs a new savings account with specified initial balance.
     * 
     * @param balance the initial balance
     */
    public SavingsAccount(double balance) {
        this.balance = new BigDecimal(String.valueOf(balance));
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
        System.out.println("Deposited $" + amount + " into Savings Account");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean withdraw(double amount) throws IllegalArgumentException {
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
            System.out.println("Withdrawn $" + amount + " from Savings Account");
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
        System.out.println("Added interest of $" + interest.setScale(2, RoundingMode.HALF_UP) + " to Savings Account with interest rate " + INTEREST_RATE.multiply(new BigDecimal("100")) + "%");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal getBalance() {
        return balance.setScale(2, RoundingMode.HALF_UP);
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
