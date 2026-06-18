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
            System.out.println("0. Back to Main Menu");
            System.out.print("Your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> createAccount(scanner);
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

}
