package fr.fms.mongodb_bank.menus;

import fr.fms.mongodb_bank.entities.Customer;
import fr.fms.mongodb_bank.services.CustomerService;
import org.springframework.stereotype.Component;

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
            System.out.println("0. Back to Main Menu");
            System.out.print("Your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> createCustomer(scanner);
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


}
