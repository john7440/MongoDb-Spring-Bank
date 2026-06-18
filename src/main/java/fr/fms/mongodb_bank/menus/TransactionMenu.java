package fr.fms.mongodb_bank.menus;

import fr.fms.mongodb_bank.entities.BankAccount;
import fr.fms.mongodb_bank.services.BankAccountService;
import fr.fms.mongodb_bank.services.CustomerService;
import fr.fms.mongodb_bank.services.TransactionService;
import fr.fms.mongodb_bank.utils.ConsoleSelectionUtil;
import fr.fms.mongodb_bank.utils.InputUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class TransactionMenu {

    private final TransactionService transactionService;
    private final BankAccountService bankAccountService;
    private final CustomerService customerService;

    public TransactionMenu(TransactionService transactionService, BankAccountService bankAccountService, CustomerService customerService) {
        this.transactionService = transactionService;
        this.bankAccountService = bankAccountService;
        this.customerService = customerService;
    }

    public void displayMenu(Scanner scanner) {
        boolean back = false;
        while (!back) {
            System.out.println("\n---- OPERATIONS MENU ----");
            System.out.println("1. Deposit");
            System.out.println("0. Back to Main Menu");
            System.out.print("Your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> performDeposit(scanner);
                case "0" -> back = true;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void performDeposit(Scanner scanner) {
        System.out.println("\n--- Make a Deposit ---");
        List<BankAccount> accounts = bankAccountService.getAllAccounts();
        BankAccount account = ConsoleSelectionUtil.selectBankAccount(scanner, accounts, customerService);
        if (account == null) return;

        double amount = InputUtil.readPositiveDouble(scanner, "Amount to deposit: ");

        System.out.print("Payment Method (Cash, Check, Bitcoin...): ");
        String method = scanner.nextLine();

        boolean success = transactionService.performDeposit(account.getId(), amount, method);
        if (success) System.out.println("Deposit successful");
        else System.out.println("Error during deposit");
    }
}
