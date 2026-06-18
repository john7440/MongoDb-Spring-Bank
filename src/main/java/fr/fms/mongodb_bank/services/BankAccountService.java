package fr.fms.mongodb_bank.services;

import fr.fms.mongodb_bank.entities.BankAccount;
import fr.fms.mongodb_bank.repositories.BankAccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public void createAccount(BankAccount account) {
        bankAccountRepository.save(account);
    }

    public List<BankAccount> getAllAccounts() {
        return bankAccountRepository.findAll();
    }

    public void updateAccount(BankAccount account) {
        bankAccountRepository.save(account);
    }

    public boolean deleteAccount(String id) {
        if (bankAccountRepository.existsById(id)) {
            bankAccountRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
