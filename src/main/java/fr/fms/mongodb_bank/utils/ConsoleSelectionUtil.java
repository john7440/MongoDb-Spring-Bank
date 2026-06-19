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

    /**
     * Displays a numbered list of customers and prompts the user to select one
     *
     * <p>Each customer is displayed with their index number, last name,
     * first name, and email address. The user is then prompted to enter the
     * corresponding number via {@link #askForIndex(Scanner, int)}</p>
     *
     * <p>Returns {@code null} if the list is null or empty, or if the user cancels
     * the selection by entering {@code 0}.</p>
     *
     * @param scanner   the {@link Scanner}
     * @param customers the list of {@link Customer} objects to display and select from
     * @return the selected {@link Customer}, or {@code null} if the list is empty
     *         or the user cancelled
     */
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

    /**
     * Prompts the user to enter a number corresponding to an item in a list
     * and returns the index of the selected item
     *
     * <p>The user is expected to enter a number between {@code 1} and {@code listSize}
      * Entering {@code 0} cancels the operation and returns {@code -1}.
     * If the input is not a valid integer or is out of the allowed range,
     * an error message is displayed and {@code -1} is returned </p>
     *
     * <p>Note: this method does <strong>not</strong> loop on invalid input —
     * it returns {@code -1} immediately on any error or cancellation</p>
     *
     * @param scanner  the {@link Scanner}
     * @param listSize the total number of items in the list
     * @return the index of the selected item,
     *         or {@code -1} if the user cancelled / invalid input
     */
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

    /**
     * Displays a numbered list of bank accounts and prompts the user to select one.
     *
     * <p>Each account is displayed with its index number, account number, current balance,
     * and the full name of its owner. The owner is resolved by querying
     * {@link CustomerService#getCustomerById(String)} using the account's customer ID.
     * If no matching customer is found, the owner is displayed as {@code "Unknown"}</p>
     *
     * <p>Returns {@code null} if the list is null or empty  or if the user cancels
     *
     * @param scanner the {@link Scanner}
     * @param accounts the list of {@link BankAccount} objects to display and select from
     * @param customerService the {@link CustomerService} used to resolve the owner
     *                        of each account by their customer ID
     * @return the selected {@link BankAccount}, or {@code null} if the list is empty
     *         or the user cancelled
     */
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

