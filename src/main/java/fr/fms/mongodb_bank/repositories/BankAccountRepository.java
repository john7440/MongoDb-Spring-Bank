package fr.fms.mongodb_bank.repositories;

import fr.fms.mongodb_bank.entities.BankAccount;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends MongoRepository<BankAccount,String> {
}
