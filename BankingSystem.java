/**
 * Banking System Application
 * 
 * This application simulates a basic banking system with checking and savings accounts.
 * Users can create accounts, perform various transactions, and manage their finances through
 * an interactive console interface.
 * 
 * Features:
 * - Account creation (Checking and Savings)
 * - Deposit and withdrawal operations
 * - Check processing (deposit and cash) for Checking accounts
 * - Interest calculation (0% for Checking accounts, 2% for Savings accounts)
 * - Balance inquiries
 * 
 * Architecture:
 * The application is built with the following class structure:
 * - BankingSystem: Main application class with user interface and control logic
 * - Bank: Maintains a collection of accounts and provides methods to add/retrieve them
 * - Account: Interface defining common banking operations
 * - SavingsAccount: Implementation of Account with 2% interest rate
 * - CheckingAccount: Implementation of Account with check processing capabilities
 * 
 * Usage:
 * The program presents a menu-driven interface where users can:
 * 1. Create a new account (Checking or Savings)
 * 2. Operate on existing Checking accounts
 * 3. Operate on existing Savings accounts
 * 
 * Data Management:
 * Account balances are stored as BigDecimal objects to maintain precision in financial calculations.
 * The Bank class uses a HashMap to store accounts, with usernames as keys.
 * 
 * Error Handling:
 * The application includes validation for:
 * - Negative amounts in financial transactions
 * - Insufficient funds for withdrawals
 * - Duplicate check processing
 * - Monetary values with more than two decimal places
 * 
 * @author Fuyuki Kobayashi (sakufuyu)
 */

 import java.util.Scanner;

/**
 * Main class for the banking system application.
 * Provides a console interface for users to create and manage bank accounts.
 */
public class BankingSystem {
    private static final Scanner userInput = new Scanner(System.in);
    private static final String CHECKING = "CHECKING";
    private static final String SAVING = "SAVING";

    /**
     * The main method that runs the banking system application.
     * 
     * @param args command line arguments (not used)
     * @throws Exception if an error occurs during execution
     */
    public static void main(String[] args) throws Exception {
        Bank bankSystem = new Bank();
        String userName;
        boolean isExit = false;

        while (!isExit) {
            System.out.println("\nBank App:");
            System.out.println("Enter choice");
            System.out.println("1. Create Account");
            System.out.println("2. Operating a Checking Account");
            System.out.println("3. Operating a Savings Account");
            System.out.println("Press other key to exit");
            System.out.print("==> ");
            String choice = userInput.nextLine();

            switch (choice) {
                case "1":
                    createAccount(bankSystem);
                    break;
                case "2":
                    userName = getUserName();
                    Account account = bankSystem.getAccount(userName + CHECKING);
                    if (account == null) {
                        continue;
                    }
                    operatingCheckingAccount(account, userName);
                    break;
                case "3":
                    userName = getUserName();
                    SavingsAccount savingsAccount = (SavingsAccount) bankSystem.getAccount(userName + SAVING);
                    if (savingsAccount == null) {
                        continue;
                    }
                    operatingSavingsAccount(savingsAccount, userName);
                    break;
                default:
                    userInput.close();
                    isExit = true;
                    break;
            }
        }
    }

    /**
     * Creates a new account in the banking system.
     * 
     * @param bankSystem the bank where the account will be created
     */
    private static void createAccount(Bank bankSystem) {
        boolean isExit = false;
        String userName;

        while (!isExit) {
            System.out.println("\nCreate Account:");
            System.out.println("Enter choice");
            System.out.println("1. Create Checking Account");
            System.out.println("2. Create Savings Account");
            System.out.println("Press other key to exit");
            System.out.print("==> ");
            String choice = userInput.nextLine();

            switch (choice) {
                case "1":
                    userName = getUserName();
                    if (bankSystem.createAccount(true, userName + CHECKING)) {
                        System.out.println("Checking account: " + userName + " created successfully");
                        isExit = true;
                    } else {
                        System.out.println("Checking account: " + userName + " already exists.");
                        System.out.println("Use other username");
                        continue;
                    }
                    break;

                case "2":
                    userName = getUserName();
                    if (bankSystem.createAccount(false, userName + SAVING)) {
                        System.out.println("Savings account: " + userName + " created successfully");
                        isExit = true;
                    } else {
                        System.out.println("Savings account: " + userName + " already exists.");
                        System.out.println("Use other username");
                        continue;
                    }
                    break;
                default:
                    isExit = true;
                    break;
            }
        }
    }

    /**
     * Gets a username from the user with validation.
     * 
     * @return a valid non-empty username
     */
    private static String getUserName() {
        String userName;
        boolean validation = false;

       do {
            System.out.print("Enter your username ==> ");
            userName = userInput.nextLine();

            if (userName == null || userName.trim().isEmpty()) {
                System.out.println("Username cannot be empty");
                continue;
            } else {
                validation = true;
            }
        } while (!validation);

        return userName.trim();
    }

    /**
     * Gets a valid monetary amount from the user.
     * 
     * @return a valid monetary amount as a double
     */
    private static double getAmount() {
        double amount = 0.0;
        boolean validation = false;

        do {
            System.out.print("Enter amount ==> ");
            String amountString = userInput.nextLine();

            try {
                amount = Double.parseDouble(amountString);
                validation = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input");
            }
        } while (!validation);

        return amount;
    }

    /**
     * Handles operations for a checking account.
     * 
     * @param account the checking account to operate on
     * @param userName the username associated with the account
     */
    private static void operatingCheckingAccount(Account account, String userName) {
        boolean isExit = false;

        while (!isExit) {
            System.out.println("\n################################");
            System.out.println("Checking account: " + userName);
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Cash Check");
            System.out.println("4. Deposit Check");
            System.out.println("5. Get Balance");
            System.out.println("6. Calculate interest");
            System.out.println("Press other key to exit");
            System.out.print("==> ");
            String choice = userInput.nextLine();

            switch (choice) {
                case "1":
                    doDeposit(account);
                    break;
                case "2":
                    doWithdraw(account);
                    break;
                case "3":
                    doCashCheck(account);
                    break;
                case "4":
                    doDepositCheck(account);
                    break;
                case "5":
                    System.out.println("Balance: " + account.getBalance());
                    break;
                case "6":
                    doCalculateInterest(account);
                    break;
                default:
                    isExit = true;
                    break;
            }
        }
    }

    /**
     * Handles operations for a savings account.
     * 
     * @param account the savings account to operate on
     * @param userName the username associated with the account
     */
    private static void operatingSavingsAccount(Account account, String userName) {
        boolean isExit = false;

        while (!isExit) {
            System.out.println("\n################################");
            System.out.println("Saving account: " + userName);
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Get Balance");
            System.out.println("4. Calculate interest");
            System.out.println("Press other key to exit");
            System.out.print("==> ");
            String choice = userInput.nextLine();

            switch (choice) {
                case "1":
                    doDeposit(account);
                    break;
                case "2":
                    doWithdraw(account);
                    break;
                case "3":
                    System.out.println("Balance: " + account.getBalance());
                    break;
                case "4":
                    doCalculateInterest(account);
                    break;
                default:
                    isExit = true;
                    break;
            }
        }
    }

    /**
     * Performs a deposit operation on an account.
     * 
     * @param account the account to deposit into
     */
    private static void doDeposit(Account account) {
        boolean isExit = false;
        while (!isExit) {
            System.out.print("Deposit: ");
            try {
                account.deposit(getAmount());
                isExit = true;
            } catch (IllegalArgumentException e) {
                System.err.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        };
    }

    /**
     * Performs a withdraw operation on an account.
     * 
     * @param account the account to withdraw from
     */
    private static void doWithdraw(Account account) {
        boolean isExit = false;
        while (!isExit) {
            System.out.print("Withdraw: ");
            try {
                isExit = account.withdraw(getAmount());
            } catch (IllegalArgumentException e) {
                System.err.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        };
    }

    /**
     * Performs a cash check operation on a checking account.
     * 
     * @param account the checking account to cash the check from
     */
    private static void doCashCheck(Account account) {
        CheckingAccount checkingAccount = (CheckingAccount) account;
        boolean isExit = false;

        while (!isExit) {
            System.out.print("Cash Check: ");
            try {
                System.out.print("Enter check number ==> ");
                String checkNumberString = userInput.nextLine();
                int checkNumber = Integer.parseInt(checkNumberString);
                isExit = checkingAccount.cashCheck(getAmount(), checkNumber);
            } catch (IllegalArgumentException e) {
                System.err.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        };
    }

    /**
     * Performs a check deposit operation on a checking account.
     * 
     * @param account the checking account to deposit the check into
     */
    private static void doDepositCheck(Account account) {
        CheckingAccount checkingAccount = (CheckingAccount) account;
        boolean isExit = false;

        while (!isExit) {
            System.out.print("Deposit Check: ");
            try {
                System.out.print("Enter check number ==> ");
                String checkNumberString = userInput.nextLine();
                int checkNumber = Integer.parseInt(checkNumberString);
                checkingAccount.depositCheck(getAmount(), checkNumber);
                isExit = true;
            } catch (IllegalArgumentException e) {
                System.err.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        };
    }

    /**
     * Calculates and adds interest to an account.
     * 
     * @param account the account to calculate interest for
     */
    private static void doCalculateInterest(Account account) {
        account.calculateInterest();
    }
}
