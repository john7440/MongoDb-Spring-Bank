package fr.fms.mongodb_bank.menus;

import fr.fms.mongodb_bank.entities.AccountStatus;
import fr.fms.mongodb_bank.entities.BankAccount;
import fr.fms.mongodb_bank.entities.Customer;
import fr.fms.mongodb_bank.services.BankAccountService;
import fr.fms.mongodb_bank.services.CustomerService;
import fr.fms.mongodb_bank.utils.ConsoleSelectionUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@Component
public class BankAccountMenu {

    private final BankAccountService bankAccountService;
    private final CustomerService customerService;

    public BankAccountMenu(BankAccountService bankAccountService, CustomerService customerService) {
        this.bankAccountService = bankAccountService;
        this.customerService = customerService;
    }

    public void displayMenu(Scanner scanner) {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- ACCOUNT MENU ---");
            System.out.println("1. Create Account");
            System.out.println("2. List all Accounts");
            System.out.println("3. Change Account Status");
            System.out.println("0. Back to Main Menu");
            System.out.print("Your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> createAccount(scanner);
                case "2" -> listAccounts();
                case "3" -> changeAccountStatus(scanner);
                case "0" -> back = true;
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void createAccount(Scanner scanner) {
        System.out.println("\n---- Create a new Account ----");

        System.out.println("Select the customer who will own this account: ");
        List<Customer> customers = customerService.getAllCustomers();
        Customer customer = ConsoleSelectionUtil.selectCustomer(scanner, customers);

        if (customer == null) return;

        System.out.print("Account Number (e.g., US123456789): ");
        String accNumber = scanner.nextLine();

        double balance = 0.0;
        System.out.print("Initial Balance: ");
        try {
            balance = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount format ! Setting balance to 0.0");
        }

        BankAccount account = new BankAccount(null, accNumber, balance, LocalDate.now(), AccountStatus.ACTIVE, customer.getId());
        bankAccountService.createAccount(account);
        System.out.println("Account created successfully for " + customer.getLastName() +  customer.getFirstName() + "!");
    }

    private void listAccounts() {
        System.out.println("\n--- List of Accounts ---");
        List<BankAccount> accounts = bankAccountService.getAllAccounts();

        if (accounts.isEmpty()) {
            System.out.println("No accounts found");
            return;
        }

        for (BankAccount acc : accounts) {
            String ownerName = "Unknown";
            Customer owner = customerService.getCustomerById(acc.getCustomerId());
            if (owner != null) {
                ownerName = owner.getFirstName() + " " + owner.getLastName().toUpperCase();
            }
            System.out.printf("%s | Balance: %.2f€ | Status: %s | Owner: %s%n",
                    acc.getAccountNumber(), acc.getBalance(), acc.getStatus(), ownerName);
        }
    }

    private void changeAccountStatus(Scanner scanner) {
        System.out.println("\n--- Change Account Status ---");
        List<BankAccount> accounts = bankAccountService.getAllAccounts();
        BankAccount account = ConsoleSelectionUtil.selectBankAccount(scanner, accounts, customerService);

        if (account == null) return;

        System.out.println("Current Status: " + account.getStatus());
        System.out.println("1. ACTIVE");
        System.out.println("2. SUSPENDED");
        System.out.println("3. CLOSED");
        System.out.print("Select new status (or 0 to cancel): ");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> account.setStatus(AccountStatus.ACTIVE);
            case "2" -> account.setStatus(AccountStatus.SUSPENDED);
            case "3" -> account.setStatus(AccountStatus.CLOSED);
            case "0" -> { return; }
            default -> {System.out.println("Invalid choice");return;}
        }

        bankAccountService.updateAccount(account);
        System.out.println("Status updated to " + account.getStatus());
    }
}
