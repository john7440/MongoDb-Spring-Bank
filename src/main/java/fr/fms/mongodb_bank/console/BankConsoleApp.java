package fr.fms.mongodb_bank.console;

import fr.fms.mongodb_bank.menus.BankAccountMenu;
import fr.fms.mongodb_bank.menus.CustomerMenu;
import fr.fms.mongodb_bank.menus.TransactionMenu;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class BankConsoleApp implements CommandLineRunner {

    private final CustomerMenu customerMenu;
    private final BankAccountMenu bankAccountMenu;
    private final TransactionMenu transactionMenu;

    public BankConsoleApp(CustomerMenu customerMenu,  BankAccountMenu bankAccountMenu, TransactionMenu transactionMenu) {
        this.customerMenu = customerMenu;
        this.bankAccountMenu = bankAccountMenu;
        this.transactionMenu = transactionMenu;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        Thread.sleep(500);

        while (running) {
            System.out.println("\n==== BANK OF AMERICA ====");
            System.out.println("1. Customer Management");
            System.out.println("2. Account Management");
            System.out.println("3. Transaction Management");
            System.out.println("0. Exit");
            System.out.print("Your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> customerMenu.displayMenu(scanner);
                case "2" -> bankAccountMenu.displayMenu(scanner);
                case "3" -> transactionMenu.displayMenu(scanner);
                case "0" -> {System.out.println("Goodbye!");running = false;}
                default -> System.out.println("Invalid choice");
            }
        }
    }
}
