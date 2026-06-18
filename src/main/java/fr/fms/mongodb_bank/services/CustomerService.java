package fr.fms.mongodb_bank.services;

import fr.fms.mongodb_bank.entities.Customer;
import fr.fms.mongodb_bank.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void createCustomer(Customer customer) {
        customerRepository.save(customer);
    }

}
