package fr.fms.mongodb_bank.console;

import fr.fms.mongodb_bank.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class BankConsoleApp implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final Scanner scanner;

    public BankConsoleApp(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
