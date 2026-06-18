package fr.fms.mongodb_bank.utils;

import fr.fms.mongodb_bank.entities.BankAccount;
import fr.fms.mongodb_bank.entities.Customer;
import fr.fms.mongodb_bank.services.CustomerService;

import java.util.List;
import java.util.Scanner;

public class ConsoleSelectionUtil {
    private ConsoleSelectionUtil() {
        /* This utility class should not be instantiated */
    }

    public static Customer selectCustomer(Scanner scanner, List<Customer> customers) {
        if (customers == null || customers.isEmpty()) {
            System.out.println("No customers found");
            return null;
        }

        System.out.println("\n----- Select a Customer ----");
        for (int i = 0; i < customers.size(); i++) {
            Customer c = customers.get(i);
            System.out.printf("%d. %s %s | %s%n", (i + 1), c.getLastName().toUpperCase(), c.getFirstName(), c.getEmail());
        }

        int index = askForIndex(scanner, customers.size());
        return (index == -1) ? null : customers.get(index);
    }

    public static int askForIndex(Scanner scanner, int listSize) {
        System.out.print("\nEnter the number (or 0 to cancel): ");
        String input = scanner.nextLine();

        try {
            int index = Integer.parseInt(input) - 1;
            if (index == -1) return -1;

            if (index >= 0 && index < listSize) {
                return index;
            } else {
                System.out.println("Number out of bounds");
                return -1;
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number");
            return -1;
        }
    }

    public static BankAccount selectBankAccount(Scanner scanner, List<BankAccount> accounts, CustomerService customerService) {
        if (accounts == null || accounts.isEmpty()) {
            System.out.println("No accounts found");
            return null;
        }
        System.out.println("\n--- Select an Account ---");
        for (int i = 0; i < accounts.size(); i++) {
            BankAccount acc = accounts.get(i);

            String ownerName = "Unknown";
            Customer owner = customerService.getCustomerById(acc.getCustomerId());
            if (owner != null) {
                ownerName = owner.getFirstName() + " " + owner.getLastName().toUpperCase();
            }
            System.out.printf("%d. %s | Balance: %.2f | Owner: %s%n",
                    (i + 1), acc.getAccountNumber(), acc.getBalance(), ownerName);
        }

        int index = askForIndex(scanner, accounts.size());
        return (index == -1) ? null : accounts.get(index);
    }
}

