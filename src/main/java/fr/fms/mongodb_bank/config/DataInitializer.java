package fr.fms.mongodb_bank.config;

import fr.fms.mongodb_bank.entities.*;
import fr.fms.mongodb_bank.repositories.BankAccountRepository;
import fr.fms.mongodb_bank.repositories.CustomerRepository;
import fr.fms.mongodb_bank.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(CustomerRepository customerRepo, BankAccountRepository accountRepo,
                                    TransactionRepository transactionRepo) {
        return args -> {
            transactionRepo.deleteAll();
            accountRepo.deleteAll();
            customerRepo.deleteAll();

            Customer jonald = new Customer(null, "Jonald", "Drump", "dj.drump@ormuz.us",
                    "Washington");
            Customer pratt = new Customer(null, "Pratt", "Bide",
                    "pratt.bide@fclub.us", "Where is my mind");
            Customer nuck = new Customer(null, "Nuck", "Norris", "nuck.norris@admin.dev",
                    "Everywhere all at once");

            customerRepo.saveAll(List.of(jonald,pratt, nuck));

            BankAccount accountJonald = new BankAccount(null, "US123456789", 7000000000.50,
                    LocalDate.now(), AccountStatus.ACTIVE, jonald.getId());
            BankAccount accountPratt = new BankAccount(null, "US987654321", 57000000.26,
                    LocalDate.now(), AccountStatus.ACTIVE, pratt.getId());
            BankAccount accountNuck = new BankAccount(null, "US0000000000", 0.0,
                    LocalDate.now(), AccountStatus.ACTIVE, nuck.getId());

            accountRepo.saveAll(List.of(accountJonald, accountPratt,accountNuck));

            Deposit deposit = new Deposit();
            deposit.setAmount(557.26);
            deposit.setTransactionDate(LocalDateTime.now());
            deposit.setDescription("Cash deposit");
            deposit.setSourceAccountId(accountJonald.getId());
            deposit.setPaymentMethod("Cash");

            Withdrawal withdrawal = new Withdrawal();
            withdrawal.setAmount(79.90);
            withdrawal.setTransactionDate(LocalDateTime.now());
            withdrawal.setDescription("ATM Withdrawal");
            withdrawal.setSourceAccountId(accountPratt.getId());
            withdrawal.setFee(1.50);

            Transfer transfer = new Transfer();
            transfer.setAmount(2000000.0);
            transfer.setTransactionDate(LocalDateTime.now());
            transfer.setDescription("In a briefcase");
            transfer.setSourceAccountId(accountJonald.getId());
            transfer.setDestinationAccountId(accountNuck.getId());
            transfer.setReason("Bribery");

            transactionRepo.saveAll(List.of(deposit, withdrawal, transfer));

            System.out.println("\nData successfully initialized !");
        };
    }
}
