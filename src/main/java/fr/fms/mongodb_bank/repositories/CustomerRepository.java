package fr.fms.mongodb_bank.repositories;

import fr.fms.mongodb_bank.entities.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository  extends MongoRepository<Customer,String> {
    List<Customer> findByLastNameContainingIgnoreCase(String lastName);
}
