package fr.fms.mongodb_bank.menus;

import fr.fms.mongodb_bank.entities.*;
import fr.fms.mongodb_bank.services.BankAccountService;
import fr.fms.mongodb_bank.services.CustomerService;
import fr.fms.mongodb_bank.services.TransactionService;
import fr.fms.mongodb_bank.utils.ConsoleSelectionUtil;
import fr.fms.mongodb_bank.utils.InputUtil;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import static fr.fms.mongodb_bank.utils.InputUtil.promptUntilValid;

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

    //----------------------transaction menu---------------------
    public void displayMenu(Scanner scanner) {
        boolean back = false;
        while (!back) {
            System.out.println("\n---- OPERATIONS MENU ----");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Transfer");
            System.out.println("4. View all transactions");
            System.out.println("0. Back to Main Menu");
            System.out.print("Your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> performDeposit(scanner);
                case "2" -> performWithdrawal(scanner);
                case "3" -> performTransfer(scanner);
                case "4" -> viewHistory(scanner);
                case "0" -> back = true;
                default -> System.out.println("Invalid choice !");
            }
        }
    }

    //--------------------Deposit -----------------------
    private void performDeposit(Scanner scanner) {
        System.out.println("\n--- Make a Deposit ---");
        List<BankAccount> accounts = bankAccountService.getAllAccounts();
        BankAccount account = ConsoleSelectionUtil.selectBankAccount(scanner, accounts, customerService);
        if (account == null) return;

        double amount = InputUtil.readPositiveDouble(scanner, "Amount to deposit: ");

        String method = promptUntilValid(scanner, "Payment Method (Cash, Check, Bitcoin...)",
                input -> !input.isBlank() && input.length() >= 2,
                "Payment method cannot be empty");

        try {
            boolean success = transactionService.performDeposit(account.getId(), amount, method);
            if (success) System.out.println("Deposit successful");
            else System.out.println("Error during deposit");
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    //------------------------- withdrawal------------------------
    private void performWithdrawal(Scanner scanner) {
        System.out.println("\n--- Make a Withdrawal ---");
        List<BankAccount> accounts = bankAccountService.getAllAccounts();
        BankAccount account = ConsoleSelectionUtil.selectBankAccount(scanner, accounts, customerService);
        if (account == null) return;

        System.out.println("Available Balance: " + account.getBalance() + "€");

        double amount = InputUtil.readPositiveDouble(scanner, "Amount to withdraw: ");
        double fee = InputUtil.readPositiveDouble(scanner, "Fee (0 if none) : ");

        try {
            boolean success = transactionService.performWithdrawal(account.getId(), amount, fee);
            if (success) System.out.println("Withdrawal successful");
            else System.out.println("Insufficient funds or error");
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    //---------------------transfer-------------------
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

        String reason = promptUntilValid(scanner, "Reason for transfer",
                input -> !input.isBlank() && input.length() >= 3,
                "Reason cannot be empty");

        try {
            boolean success = transactionService.performTransfer(source.getId(), dest.getId(), amount, reason);
            if (success) System.out.println("Transfer successful");
            else System.out.println("Insufficient funds or error!");
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    //---------------------history --------------------
    private void viewHistory(Scanner scanner) {
        System.out.println("\n--- Account History ---");
        List<BankAccount> accounts = bankAccountService.getAllAccounts();
        BankAccount account = ConsoleSelectionUtil.selectBankAccount(scanner, accounts, customerService);
        if (account == null) return;

        List<Transaction> history = transactionService.getAccountHistory(account.getId());

        System.out.println("\n======= TRANSACTION HISTORY =======");
        if (history.isEmpty()) {
            System.out.println("No transactions found for this account.");
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            for (Transaction t : history) {
                String dateStr = t.getTransactionDate().format(formatter);

                if (t instanceof Deposit d) {
                    System.out.printf("[%s]DEPOSIT  : +%.2f€ (Method: %s)%n", dateStr, d.getAmount(), d.getPaymentMethod());
                }
                else if (t instanceof Withdrawal w) {
                    System.out.printf("[%s] WITHDRAW : -%.2f€ (Fee: %.2f€)%n", dateStr, w.getAmount(), w.getFee());
                }
                else if (t instanceof Transfer tr) {
                    if (tr.getSourceAccountId().equals(account.getId())) {
                        System.out.printf("[%s]OUTGOING TRANSFER : -%.2f€ (Reason: %s)%n", dateStr, tr.getAmount(), tr.getReason());
                    } else {
                        System.out.printf("[%s]INCOMING TRANSFER : +%.2f€ (Reason: %s)%n", dateStr, tr.getAmount(), tr.getReason());
                    }
                }
            }
        }
        System.out.println("==================================");
    }
}
