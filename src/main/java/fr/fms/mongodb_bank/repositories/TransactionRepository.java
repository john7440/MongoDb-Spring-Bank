package fr.fms.mongodb_bank.repositories;

import fr.fms.mongodb_bank.entities.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TransactionRepository extends MongoRepository<Transaction,String> {
}
