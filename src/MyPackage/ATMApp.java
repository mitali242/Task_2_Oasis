package MyPackage;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
public class ATMApp {
	private String userID;
    private String pinNumber;
    private double accountBalance;
    private ArrayList<String> transactionHistory;
    private Map<String, Double> accountBalances;
    
    public ATMApp(String userID, String pinNumber) {
        this.userID = userID;
        this.pinNumber = pinNumber;
        accountBalance = 0.0;
        transactionHistory = new ArrayList<>();
    }
    
    public ATMApp() {
        accountBalance = 0.0;
        transactionHistory = new ArrayList<>();
        accountBalances = new HashMap<>();
        // Initialize some sample account balances
        accountBalances.put("1234", 100000.0);
        accountBalances.put("9090", 5000000.0);
    }

    public void displayMenu() {
        System.out.println("ATM Menu:");
        System.out.println("1. Deposit");
        System.out.println("2. Withdrawal");
        System.out.println("3. Transaction History");
        System.out.println("4. Transfer Amount");
        System.out.println("5. Check Balance");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    public void executeChoice(int choice) {
        Scanner scanner = new Scanner(System.in);

        switch (choice) {
            case 1:
                System.out.print("Enter the deposit amount: ");
                double depositAmount = scanner.nextDouble();
                Deposit deposit = new Deposit(accountBalance, transactionHistory);
                deposit.depositAmount(depositAmount);
                accountBalance = deposit.getAccountBalance();
                break;

            case 2:
                System.out.print("Enter the withdrawal amount: ");
                double withdrawalAmount = scanner.nextDouble();
                Withdrawal withdrawal = new Withdrawal(accountBalance, transactionHistory);
                withdrawal.withdrawAmount(withdrawalAmount);
                accountBalance = withdrawal.getAccountBalance();
                break;

            case 3:
                TransactionHistory transactionHistoryObj = new TransactionHistory(transactionHistory);
                transactionHistoryObj.displayTransactionHistory();
                break;

            case 4:
                System.out.print("Enter the transfer amount: ");
                double transferAmount = scanner.nextDouble();
                System.out.print("Enter the recipient account number: ");
                String recipientAccount = scanner.next();
                TransferAmount transfer = new TransferAmount(accountBalance, transactionHistory);
                transfer.transferFunds(transferAmount, recipientAccount);
                accountBalance = transfer.getAccountBalance();
                break;

            case 5:
                checkBalance(accountBalance);
                break;
            
                
            case 6:
                System.out.println("Exiting ATM. Thank you!");
                break;

            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter their user ID and PIN number
        System.out.print("Enter your user ID: ");
        String userID = scanner.nextLine();

        System.out.print("Enter your PIN number: ");
        String pinNumber = scanner.nextLine();

        // Authenticate the user
        if (authenticateUser(userID, pinNumber)) {
            ATMApp atm = new ATMApp(userID, pinNumber);
            int choice = 0;

            while (choice != 6) {
                atm.displayMenu();
                choice = scanner.nextInt();
                atm.executeChoice(choice);
            }
        } else {
            System.out.println("Invalid user ID or PIN number. Exiting ATM.");
        }
    }
    private static boolean authenticateUser(String userID, String pinNumber) {
        return userID.equals("1234") && pinNumber.equals("5678");
    }
    public void checkBalance(double accountBalance) {
            System.out.println("Your current balance is: $" + accountBalance);
        
}

class Deposit {
    private double accountBalance;
    private ArrayList<String> transactionHistory;

    public Deposit(double accountBalance, ArrayList<String> transactionHistory) {
        this.accountBalance = accountBalance;
        this.transactionHistory = transactionHistory;
    }

    public void depositAmount(double amount) {
        accountBalance += amount;
        transactionHistory.add("Deposit: +" + amount);
        System.out.println("Deposit successful!");
    }

    public double getAccountBalance() {
        return accountBalance;
    }
}

class Withdrawal {
    private double accountBalance;
    private ArrayList<String> transactionHistory;

    public Withdrawal(double accountBalance, ArrayList<String> transactionHistory) {
        this.accountBalance = accountBalance;
        this.transactionHistory = transactionHistory;
    }

    public void withdrawAmount(double amount) {
        if (amount <= accountBalance) {
            accountBalance -= amount;
            transactionHistory.add("Withdrawal: -" + amount);
            //checkBalance(accountBalance);
            System.out.println("Withdrawal successful!");
        } else {
            System.out.println("Insufficient funds!");
        }
    }

    public double getAccountBalance() {
        return accountBalance;
    }
}

class TransactionHistory {
    private ArrayList<String> transactionHistory;

    public TransactionHistory(ArrayList<String> transactionHistory) {
        this.transactionHistory = transactionHistory;
    }

    public void displayTransactionHistory() {
        System.out.println("Transaction History:");
        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }
}

class TransferAmount {
    private double accountBalance;
    private ArrayList<String> transactionHistory;

    public TransferAmount(double accountBalance, ArrayList<String> transactionHistory) {
        this.accountBalance = accountBalance;
        this.transactionHistory = transactionHistory;
    }

    public void transferFunds(double amount, String recipientAccountNumber) {
        if (amount <= accountBalance) {
            accountBalance -= amount;
            transactionHistory.add("Transfer: -" + amount + " to account " + recipientAccountNumber);
           checkBalance(accountBalance);
            System.out.println("Transfer successful!");
            TransferAmount recipientAccount = this; // Just for demonstration purposes
            recipientAccount.depositUs(amount);
        } else {
            System.out.println("Insufficient funds!");
        }
    }

    public void depositUs(double amount) {
        accountBalance += amount;
        transactionHistory.add("Deposit (Transfer Received): +" + amount);
    }

    public double getAccountBalance() {
        return accountBalance;
    }
}
}


  
