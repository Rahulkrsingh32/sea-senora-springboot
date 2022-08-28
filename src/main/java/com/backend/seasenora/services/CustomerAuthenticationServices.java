package com.backend.seasenora.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.seasenora.entities.Customer;
import com.backend.seasenora.entities.Role;
import com.backend.seasenora.exceptions.CustomerALLReadyExistException;
import com.backend.seasenora.exceptions.CustomerNotFoundException;
import com.backend.seasenora.model.CustomerRequest;
import com.backend.seasenora.repositories.CustomerRepository;

@Service
@Transactional
public class CustomerAuthenticationServices implements UserDetailsService {

    final private static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
            .getLogger(CustomerAuthenticationServices.class);

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Customer customer = customerRepo.findByUsername(username)
                .orElseThrow(() -> new CustomerNotFoundException("Customer Not Found with the username" + username));
        List<Role> roles = customer.getRoles()
                .stream().collect(Collectors.toList());
        List<GrantedAuthority> grantedAuthorities = roles.stream().map(role -> {
            return new SimpleGrantedAuthority(role.getRoleName());
        }).collect(Collectors.toList());
        UserDetails userDetails = new User(username, customer.getPassword(), grantedAuthorities);
        log.info("Customer with %1$s is present", username);
        return userDetails;

    }

    public void registerUser(CustomerRequest customerRequest) {
        Optional<Customer> customer = customerRepo.findByUsername(customerRequest.getUsername());
        if (customer.isPresent()) {
            log.error("Customer with %1$s Already Exist by Trying to Signup again ", customerRequest.getUsername());
            throw new CustomerALLReadyExistException("User Already Exist");
        }
        Customer customer_temp = new Customer();
        customer_temp.setName(customerRequest.getName());
        customer_temp.setEmailId(customerRequest.getEmailId());
        customer_temp.setPhoneNumber(customerRequest.getPhoneNumber());
        customer_temp.setUsername(customerRequest.getUsername());
        customer_temp.setPassword(passwordEncoder.encode(customerRequest.getPassword()));
        customer_temp.setRoles(customerRequest.getRoles().stream().map(role -> {
            Role role_temp = new Role();
            role_temp.setRoleName(role);
            role_temp.setCustomer(customer_temp);
            return role_temp;
        }).collect(Collectors.toSet()));
        log.info("Customer %1$s Registration is in progress", customerRequest.getUsername());
        customerRepo.save(customer_temp);
        log.info(customer_temp.getUsername(), "%1$s Registered Successfully");
    }

}
