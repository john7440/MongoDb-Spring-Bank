package fr.fms.mongodb_bank.services;

import fr.fms.mongodb_bank.entities.BankAccount;
import fr.fms.mongodb_bank.entities.Deposit;
import fr.fms.mongodb_bank.entities.Withdrawal;
import fr.fms.mongodb_bank.repositories.BankAccountRepository;
import fr.fms.mongodb_bank.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final BankAccountRepository bankAccountRepository;

    public TransactionService(TransactionRepository transactionRepository, BankAccountRepository bankAccountRepository) {
        this.transactionRepository = transactionRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    public boolean performDeposit(String accountId, double amount, String paymentMethod) {
        Optional<BankAccount> optAccount = bankAccountRepository.findById(accountId);
        if (optAccount.isEmpty()) return false;

        BankAccount account = optAccount.get();

        account.setBalance(account.getBalance() + amount);
        bankAccountRepository.save(account);

        Deposit deposit = new Deposit();
        deposit.setSourceAccountId(accountId);
        deposit.setAmount(amount);
        deposit.setTransactionDate(LocalDateTime.now());
        deposit.setDescription("Counter Deposit");
        deposit.setPaymentMethod(paymentMethod);
        transactionRepository.save(deposit);

        return true;
    }

    public boolean performWithdrawal(String accountId, double amount, double fee) {
        Optional<BankAccount> optAccount = bankAccountRepository.findById(accountId);
        if (optAccount.isEmpty()) return false;

        BankAccount account = optAccount.get();
        double totalDeduction = amount + fee;

        if (account.getBalance() < totalDeduction) return false;

        account.setBalance(account.getBalance() - totalDeduction);
        bankAccountRepository.save(account);

        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setSourceAccountId(accountId);
        withdrawal.setAmount(amount);
        withdrawal.setTransactionDate(LocalDateTime.now());
        withdrawal.setDescription("New Withdrawal");
        withdrawal.setFee(fee);
        transactionRepository.save(withdrawal);

        return true;
    }


}
