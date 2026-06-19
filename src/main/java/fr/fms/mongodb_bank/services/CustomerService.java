package fr.fms.mongodb_bank.services;

import fr.fms.mongodb_bank.entities.AccountStatus;
import fr.fms.mongodb_bank.entities.BankAccount;
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
    private final BankAccountService bankAccountService;

    public CustomerService(CustomerRepository customerRepository, BankAccountService bankAccountService) {
        this.customerRepository = customerRepository;
        this.bankAccountService = bankAccountService;
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
        if (!customerRepository.existsById(id)) return false;

        List<BankAccount> accounts = bankAccountService.getAccountsByCustomerId(id);

        boolean hasActiveAccount = accounts.stream().anyMatch(a ->
                                    a.getStatus() == AccountStatus.ACTIVE);
        if (hasActiveAccount) {
            throw new IllegalStateException("Cannot delete customer: they still have ACTIVE account! " +
                    "Close them first");
        }

        boolean hasPositiveBalance = accounts.stream().anyMatch(a -> a.getBalance() > 0);
        if (hasPositiveBalance) {
            throw new IllegalStateException("Cannot delete customer: one or more accounts still have a positive balance.");
        }

        customerRepository.deleteById(id);
        return true;
    }
}
