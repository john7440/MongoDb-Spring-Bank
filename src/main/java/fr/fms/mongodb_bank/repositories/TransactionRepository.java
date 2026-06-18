package fr.fms.mongodb_bank.repositories;

import fr.fms.mongodb_bank.entities.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TransactionRepository extends MongoRepository<Transaction,String> {
    @Query(value = "{ '$or': [ { 'sourceAccountId': ?0 }, { 'destinationAccountId': ?0 } ] }",
            sort = "{ 'transactionDate': -1 }")
    List<Transaction> findAccountHistory(String accountId);
}
