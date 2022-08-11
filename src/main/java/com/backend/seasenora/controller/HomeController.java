package com.backend.seasenora.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.seasenora.entities.Customer;
import com.backend.seasenora.model.CustomerRequest;
import com.backend.seasenora.model.CustomerRequestSignin;
import com.backend.seasenora.model.CustomerResponse;
import com.backend.seasenora.model.SuccessResponse;
import com.backend.seasenora.services.CustomerAuthenticationServices;
import com.backend.seasenora.services.CustomerService;
import com.backend.seasenora.util.JwtUtil;

@RestController
@RequestMapping("/")
@SuppressWarnings("rawtypes")
public class HomeController {

    final private static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
            .getLogger(HomeController.class);

    @Autowired
    private CustomerService custService;

    @Autowired
    private CustomerAuthenticationServices customerAuthService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signin")
    public ResponseEntity<CustomerResponse> signIn(@RequestBody CustomerRequestSignin customerRequestSignin) {

        Authentication authentication = null;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(customerRequestSignin.getUsername(),
                            customerRequestSignin.getPassword()));

        } catch (BadCredentialsException e) {
            log.error("Customer" + customerRequestSignin.getUsername() + "Has entered Invalid Password");
            throw new BadCredentialsException("Invalid Credentials");

        }

        String token = jwtUtil.generateToken(authentication);
        User user = (User) authentication.getPrincipal();
        List<String> roles = user.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.toList());

        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setToken(token);
        customerResponse.setRoles(roles);
        customerResponse.setCustomerID(custService.getCustomerByUserName(user.getUsername()).getId());
        log.info("customer" + user.getUsername() + "authenticated Successfully");
        return ResponseEntity.ok(customerResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<SuccessResponse> signUp(@RequestBody CustomerRequest customerRequest) {
        customerAuthService.registerUser(customerRequest);
        log.info(customerRequest.getUsername() + " " + "registered Successfully");
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setSuccessMessage("User Registered Successfully");
        return new ResponseEntity<SuccessResponse>(successResponse, HttpStatus.CREATED);
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/customer")
    public ResponseEntity<List<Customer>> getCustomers() {
        List<Customer> customers = custService.getCustomer();
        return new ResponseEntity(customers, HttpStatus.OK);
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") int customerId) {
        Customer customer = custService.getCustomerById(customerId);
        return new ResponseEntity(customer, HttpStatus.OK);
    }

    @DeleteMapping("/customer/{id}")
    public ResponseEntity deleteCustomer(@PathVariable("id") int customerId) {
        custService.deleteCustomerById(customerId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/customers/update")
    public ResponseEntity updateCustomer(@RequestBody Customer customer) {
        custService.updateCustomer(customer.getId(), customer);
        return new ResponseEntity(HttpStatus.OK);
    }

}
