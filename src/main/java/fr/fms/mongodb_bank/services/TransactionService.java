package fr.fms.mongodb_bank.services;

import fr.fms.mongodb_bank.entities.*;
import fr.fms.mongodb_bank.repositories.BankAccountRepository;
import fr.fms.mongodb_bank.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final BankAccountRepository bankAccountRepository;

    public TransactionService(TransactionRepository transactionRepository, BankAccountRepository bankAccountRepository) {
        this.transactionRepository = transactionRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    //--------------------------------------deposit --------------------------------------
    public boolean performDeposit(String accountId, double amount, String paymentMethod) {
        Optional<BankAccount> optAccount = bankAccountRepository.findById(accountId);
        if (optAccount.isEmpty()) return false;

        BankAccount account = optAccount.get();

        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new IllegalStateException("Account is " + account.getStatus() +
                    "! Only ACTIVE accounts can receive deposits");
        }

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

    //-----------------------------Withdrawal-------------------------------------
    public boolean performWithdrawal(String accountId, double amount, double fee) {
        Optional<BankAccount> optAccount = bankAccountRepository.findById(accountId);
        if (optAccount.isEmpty()) return false;

        BankAccount account = optAccount.get();

        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new IllegalStateException("Account is " + account.getStatus() +
                    "! Withdrawals are not allowed!");
        }

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

    //------------------------------transfer ----------------------------------------------------
    public boolean performTransfer(String sourceId, String destId, double amount, String reason) {
        Optional<BankAccount> optSource = bankAccountRepository.findById(sourceId);
        Optional<BankAccount> optDest = bankAccountRepository.findById(destId);

        if (optSource.isEmpty() || optDest.isEmpty()) return false;

        BankAccount source = optSource.get();
        BankAccount dest = optDest.get();

        if (source.getStatus() != AccountStatus.ACTIVE) {
            throw new IllegalStateException("Source account is " + source.getStatus() + "!");
        }
        if (dest.getStatus() != AccountStatus.ACTIVE) {
            throw new IllegalStateException("Destination account is " + dest.getStatus() + "!");
        }

        if (source.getBalance() < amount) return false;

        source.setBalance(source.getBalance() - amount);
        dest.setBalance(dest.getBalance() + amount);
        bankAccountRepository.save(source);
        bankAccountRepository.save(dest);

        Transfer transfer = new Transfer();
        transfer.setSourceAccountId(sourceId);
        transfer.setDestinationAccountId(destId);
        transfer.setAmount(amount);
        transfer.setTransactionDate(LocalDateTime.now());
        transfer.setDescription("Transfer");
        transfer.setReason(reason);
        transactionRepository.save(transfer);

        return true;
    }

    //---------------------------history--------------------------
    public List<Transaction> getAccountHistory(String accountId) {
        return transactionRepository.findAccountHistory(accountId);
    }
}
