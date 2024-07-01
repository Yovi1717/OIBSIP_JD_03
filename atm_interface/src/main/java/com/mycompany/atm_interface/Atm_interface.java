package com.mycompany.atm_interface;

import java.util.ArrayList;
import java.util.Scanner;

// Class to represent an Account
class Account {
    private String userId;
    private String userPin;
    private double balance;
    private ArrayList<String> transactionHistory;

    public Account(String userId, String userPin, double initialBalance) {
        this.userId = userId;
        this.userPin = userPin;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public boolean validatePin(String pin) {
        return userPin.equals(pin);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add("Deposited: $" + amount);
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactionHistory.add("Withdrew: $" + amount);
            return true;
        }
        return false;
    }

    public void transfer(Account recipient, double amount) {
        if (withdraw(amount)) {
            recipient.deposit(amount);
            transactionHistory.add("Transferred: $" + amount + " to " + recipient.getUserId());
        }
    }

    public void printTransactionHistory() {
        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }
}

// Main ATM class
public class Atm_interface {
    private static ArrayList<Account> accounts = new ArrayList<>();

    public static void main(String[] args) {
        // Sample accounts
        accounts.add(new Account("user1", "1234", 1000.00));
        accounts.add(new Account("user2", "5678", 500.00));

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter user id: ");
        String userId = sc.next();
        System.out.print("Enter user pin: ");
        String userPin = sc.next();

        Account account = validateUser(userId, userPin);

        if (account != null) {
            showMenu(account);
        } else {
            System.out.println("Invalid user id or pin.");
        }
        sc.close();
    }

    private static Account validateUser(String userId, String userPin) {
        for (Account account : accounts) {
            if (account.getUserId().equals(userId) && account.validatePin(userPin)) {
                return account;
            }
        }
        return null;
    }

    private static void showMenu(Account account) {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\nATM Menu:");
            System.out.println("1. Transactions History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Choose an option: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    account.printTransactionHistory();
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = sc.nextDouble();
                    if (account.withdraw(withdrawAmount)) {
                        System.out.println("Withdrew $" + withdrawAmount + " successfully.");
                    } else {
                        System.out.println("Insufficient balance.");
                    }
                    break;
                case 3:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = sc.nextDouble();
                    account.deposit(depositAmount);
                    System.out.println("Deposited $" + depositAmount + " successfully.");
                    break;
                case 4:
                    System.out.print("Enter recipient user id: ");
                    String recipientId = sc.next();
                    System.out.print("Enter amount to transfer: ");
                    double transferAmount = sc.nextDouble();
                    Account recipient = getAccountById(recipientId);
                    if (recipient != null) {
                        account.transfer(recipient, transferAmount);
                        System.out.println("Transferred $" + transferAmount + " to " + recipientId + " successfully.");
                    } else {
                        System.out.println("Recipient not found.");
                    }
                    break;
                case 5:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (choice != 5);
        sc.close();
    }

    private static Account getAccountById(String userId) {
        for (Account account : accounts) {
            if (account.getUserId().equals(userId)) {
                return account;
            }
        }
        return null;
    }
}
