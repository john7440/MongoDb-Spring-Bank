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
            System.out.println("2. Withdraw");
            System.out.println("3. Transfer");
            System.out.println("0. Back to Main Menu");
            System.out.print("Your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> performDeposit(scanner);
                case "2" -> performWithdrawal(scanner);
                case "3" -> performTransfer(scanner);
                case "0" -> back = true;
                default -> System.out.println("Invalid choice !");
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

    private void performWithdrawal(Scanner scanner) {
        System.out.println("\n--- Make a Withdrawal ---");
        List<BankAccount> accounts = bankAccountService.getAllAccounts();
        BankAccount account = ConsoleSelectionUtil.selectBankAccount(scanner, accounts, customerService);
        if (account == null) return;

        System.out.println("Available Balance: " + account.getBalance() + "€");

        double amount = InputUtil.readPositiveDouble(scanner, "Amount to withdraw: ");
        double fee = InputUtil.readPositiveDouble(scanner, "Fee (0 if none) : ");

        boolean success = transactionService.performWithdrawal(account.getId(), amount, fee);
        if (success) System.out.println("Withdrawal successful!");
        else System.out.println("Insufficient funds or error");
    }

    private void performTransfer(Scanner scanner) {
        System.out.println("\n--- Make a Transfer ---");
        List<BankAccount> accounts = bankAccountService.getAllAccounts();

        System.out.println("[1/2] Select SOURCE Account:");
        BankAccount source = ConsoleSelectionUtil.selectBankAccount(scanner, accounts, customerService);
        if (source == null) return;

        System.out.println("\n[2/2] Select DESTINATION Account:");
        BankAccount dest = ConsoleSelectionUtil.selectBankAccount(scanner, accounts, customerService);
        if (dest == null) return;

        if (source.getId().equals(dest.getId())) {
            System.out.println("Source and destination cannot be the same");
            return;
        }

        System.out.println("Available Balance: " + source.getBalance() + "€");

        double amount = InputUtil.readPositiveDouble(scanner, "Amount to transfer : ");

        System.out.print("Reason for transfer: ");
        String reason = scanner.nextLine();

        boolean success = transactionService.performTransfer(source.getId(), dest.getId(), amount, reason);
        if (success) System.out.println("Transfer successful!");
        else System.out.println("Insufficient funds or error");
    }
}
