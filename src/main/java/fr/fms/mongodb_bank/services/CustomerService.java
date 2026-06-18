package fr.fms.mongodb_bank.services;

import fr.fms.mongodb_bank.entities.Customer;
import fr.fms.mongodb_bank.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void createCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public List<Customer> searchCustomersByLastName(String lastName) {
        return customerRepository.findByLastNameContainingIgnoreCase(lastName);
    }

    public void updateCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public boolean deleteCustomer(String id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
