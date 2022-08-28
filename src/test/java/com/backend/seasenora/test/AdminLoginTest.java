package com.backend.seasenora.test;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import com.backend.seasenora.repositories.CustomerRepository;
import com.backend.seasenora.entities.Customer;
import com.backend.seasenora.model.CustomerRequestSignin;
import org.springframework.beans.factory.annotation.Autowired;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class AdminLoginTest {
	private Customer customer;
	
	private CustomerRepository repo;
    @Test
    void AdminLoginT() throws Exception {
        boolean actualResult;
        Optional<Customer> alm=repo.findByUsername("admin");
        if(customer.getPassword().equals("admin@123"))
        {
            actualResult=true;
        }else{
            actualResult=false;
        }
        assertEquals(true,actualResult);
    }

}
