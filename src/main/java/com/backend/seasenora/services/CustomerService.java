package com.backend.seasenora.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.seasenora.entities.Customer;
import com.backend.seasenora.exceptions.CustomerNotFoundException;
import com.backend.seasenora.repositories.CustomerRepository;

@Service
@Transactional
public class CustomerService {

    final private static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
            .getLogger(CustomerService.class);

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Customer getCustomerById(int customerId) {
        return customerRepo.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer Not Found"));
    }

    public Customer getCustomerByUserName(String username) {
        return customerRepo.findByUsername(username)
                .orElseThrow(() -> new CustomerNotFoundException("Customer Not Found"));
    }

    public List<Customer> getCustomer() {
        return customerRepo.findAll();
    }

    public void deleteCustomerById(int customerId) {
        customerRepo.deleteById(customerId);
        log.info("Customer with id" + customerId + "deleted Successfully");
    }

    public void updateCustomer(int id, Customer cust) {
        Customer cust_temp = customerRepo.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer Not Found"));
        cust_temp.setName(cust.getName());
        cust_temp.setEmailId(cust.getEmailId());
        cust_temp.setPhoneNumber(cust.getPhoneNumber());
        cust_temp.setPassword(cust.getPassword());
        customerRepo.save(cust_temp);
        log.info(cust_temp.getUsername() + "Details Update Successfully");

    }

    public void saveCustomer(Customer customer) {
        customerRepo.save(customer);
        log.info("Customer details saved successfully" + customer.getUsername());
    }

    public boolean verifyCustomerEmail(String emailId) {
        Customer customer = customerRepo.findByEmailId(emailId);
        if (customer != null) {
            return true;
        } else {
            return false;
        }
    }

}