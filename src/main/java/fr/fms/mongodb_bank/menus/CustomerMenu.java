package fr.fms.mongodb_bank.menus;

import fr.fms.mongodb_bank.entities.Customer;
import fr.fms.mongodb_bank.services.CustomerService;
import fr.fms.mongodb_bank.utils.ConsoleSelectionUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class CustomerMenu {

    private final CustomerService customerService;

    public CustomerMenu(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void displayMenu(Scanner scanner) {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- CUSTOMER MENU ---");
            System.out.println("1. Create Customer");
            System.out.println("2. View Customers");
            System.out.println("3. Search Customer");
            System.out.println("4. Modify a Customer");
            System.out.println("5. Delete a Customer");
            System.out.println("0. Back to Main Menu");
            System.out.print("Your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> createCustomer(scanner);
                case "2" -> listCustomers();
                case "3" -> searchCustomer(scanner);
                case "4" -> updateCustomer(scanner);
                case "5" -> deleteCustomer(scanner);
                case "0" -> back = true;
                default -> System.out.println("Invalid choice !\n");
            }
        }
    }

    private void createCustomer(Scanner scanner) {
        System.out.println("\n---- Create a new Customer ---");
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();

        Customer customer = new Customer(null, firstName, lastName, email, address);

        customerService.createCustomer(customer);
        System.out.println("Customer created successfully !\n");
    }

    private void listCustomers() {
        System.out.println("\n--- List of Customers ---");
        List<Customer> customers = customerService.getAllCustomers();

        if (customers.isEmpty()) {
            System.out.println("No customers found");
            return;
        }
        for (Customer c : customers) {
            System.out.printf("%s %s | %s%n",  c.getLastName().toUpperCase(), c.getFirstName(), c.getEmail());
        }
    }

    private void searchCustomer(Scanner scanner) {
        System.out.print("\nEnter Last Name (or part of it) to search: ");
        String lastName = scanner.nextLine();

        List<Customer> customers = customerService.searchCustomersByLastName(lastName);

        if (customers.isEmpty()) {
            System.out.println("No matching customers !");
        } else {
            for (Customer c : customers) {
                System.out.printf("%s %s | %s%n",c.getLastName().toUpperCase(), c.getFirstName(), c.getAddress());
            }
        }
    }

    private void updateCustomer(Scanner scanner) {
        System.out.println("\n---- Modify a Customer ---");

        List<Customer> allCustomers = customerService.getAllCustomers();

        Customer customer = ConsoleSelectionUtil.selectCustomer(scanner, allCustomers);
        if (customer == null) return;

        System.out.printf("New First Name (%s): ", customer.getFirstName());
        String firstName = scanner.nextLine();
        if (!firstName.trim().isEmpty()) customer.setFirstName(firstName);

        System.out.printf("New Last Name (%s): ", customer.getLastName());
        String lastName = scanner.nextLine();
        if (!lastName.trim().isEmpty()) customer.setLastName(lastName);

        System.out.printf("New Email (%s): ", customer.getEmail());
        String email = scanner.nextLine();
        if (!email.trim().isEmpty()) customer.setEmail(email);

        System.out.printf("New Address (%s): ", customer.getAddress());
        String address = scanner.nextLine();
        if (!address.trim().isEmpty()) customer.setAddress(address);

        customerService.updateCustomer(customer);
        System.out.println("Customer updated");
    }

    private void deleteCustomer(Scanner scanner) {
        System.out.println("\n--- Delete a Customer ----");

        List<Customer> allCustomers = customerService.getAllCustomers();

        Customer customer = ConsoleSelectionUtil.selectCustomer(scanner, allCustomers);
        if (customer == null) return;

        boolean deleted = customerService.deleteCustomer(customer.getId());
        if (deleted) {
            System.out.println("Customer deleted successfully");
        } else {
            System.out.println("Customer not found !");
        }
    }

}
