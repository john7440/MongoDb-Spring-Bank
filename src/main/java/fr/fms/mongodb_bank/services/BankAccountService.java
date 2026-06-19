package fr.fms.mongodb_bank.services;

import fr.fms.mongodb_bank.entities.AccountStatus;
import fr.fms.mongodb_bank.entities.BankAccount;
import fr.fms.mongodb_bank.repositories.BankAccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }
    //---------------------create------------------------------------
    public void createAccount(BankAccount account) {
        bankAccountRepository.save(account);
    }

    //------------------------ read all ---------------------------------------
    public List<BankAccount> getAllAccounts() {
        return bankAccountRepository.findAll();
    }

    //------------------------ update-----------------------------------
    public void updateAccount(BankAccount account) {
        bankAccountRepository.save(account);
    }

    //-------------------------- delete -----------------
    public boolean deleteAccount(String id) {
        Optional<BankAccount> opt = bankAccountRepository.findById(id);
        if (opt.isEmpty()) return false;

        BankAccount account = opt.get();

        if (account.getBalance() > 0) {
            throw new IllegalStateException(
                    "Cannot delete account with positive balance (%.2f€)! Please withdraw first"
                            .formatted(account.getBalance()));
        }

        if (account.getStatus() == AccountStatus.ACTIVE) {
            throw new IllegalStateException("Cannot delete an ACTIVE account! Suspend or close it first !");
        }

        bankAccountRepository.deleteById(id);
        return true;
    }

    public List<BankAccount> getAccountsByCustomerId(String customerId) {
        return bankAccountRepository.findByCustomerId(customerId);
    }
}
