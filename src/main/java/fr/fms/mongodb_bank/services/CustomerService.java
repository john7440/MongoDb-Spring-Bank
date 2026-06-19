package fr.fms.mongodb_bank.services;

import fr.fms.mongodb_bank.entities.Customer;
import fr.fms.mongodb_bank.repositories.CustomerRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    //---------------------------create-----------------------------------------
    public void createCustomer(@Valid Customer customer) {
        customerRepository.save(customer);
    }

    //------------------------------ read all-----------------------------------
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    //------------------------------read by id ------------------------------------------
    public Customer getCustomerById(String id) {
        return customerRepository.findById(id).orElse(null);
    }

    //---------------------------- search-------------------------------------------
    public List<Customer> searchCustomersByLastName(String lastName) {
        return customerRepository.findByLastNameContainingIgnoreCase(lastName);
    }

    //------------------------------update----------------------------
    public void updateCustomer(@Valid Customer customer) {
        customerRepository.save(customer);
    }

    //---------------------------delete ----------------
    public boolean deleteCustomer(String id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
