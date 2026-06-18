package fr.fms.mongodb_bank.repositories;

import fr.fms.mongodb_bank.entities.BankAccount;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankAccountRepository extends MongoRepository<BankAccount,String> {
    List<BankAccount> findByCustomerId(String customerId);
}
