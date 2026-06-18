package fr.fms.mongodb_bank.utils;

import fr.fms.mongodb_bank.entities.Customer;

import java.util.List;
import java.util.Scanner;

public class ConsoleSelectionUtil {

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

    private static int askForIndex(Scanner scanner, int listSize) {
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
}
