package fr.fms.mongodb_bank.services;

import fr.fms.mongodb_bank.entities.BankAccount;
import fr.fms.mongodb_bank.repositories.BankAccountRepository;
import org.springframework.stereotype.Service;

@Service
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public BankAccount createAccount(BankAccount account) {
        return bankAccountRepository.save(account);
    }
}
