package com.backend.seasenora.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.backend.seasenora.entities.Boats;
import com.backend.seasenora.entities.Bookings;
import com.backend.seasenora.entities.Customer;
import com.backend.seasenora.exceptions.BoatNotFoundException;
import com.backend.seasenora.exceptions.UserNotFoundException;
import com.backend.seasenora.repositories.BoatsRepository;
import com.backend.seasenora.repositories.BookingsRepository;
import com.backend.seasenora.repositories.CustomerRepository;

@RestController
public class BookingsController {
	
	@Autowired
	private BookingsRepository bookingsRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private BoatsRepository boatsRepository;
	
	@PostMapping("/usr/{id}/bookings/boat/{bid}")
	public ResponseEntity<Object> createBookings(@PathVariable int id, @PathVariable int bid, @RequestBody Bookings bookings) throws BoatNotFoundException {
		Optional<Customer> userOptional = customerRepository.findById(id);
		Optional<Boats> boatsOptional = boatsRepository.findById(bid);
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("id-"+ id);
		}
		
		if(!boatsOptional.isPresent()) {
			throw new BoatNotFoundException("id-"+ bid);
		}
		
		Customer customer = userOptional.get();
		Boats boats = boatsOptional.get();
		bookings.setCustomer(customer);
		bookings.setBoats(boats);
		bookingsRepository.save(bookings);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(bookings.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/usr/{id}/bookings")
	public List<Bookings> getBookingById(@PathVariable int id){
		Optional<Customer> userOptional = customerRepository.findById(id);
		
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("id-"+ id);
		}
		
		
		return userOptional.get().getBookings();
	}
	
	@DeleteMapping("/bookings/{bid}/cancel")
	public void cancelBooking(@PathVariable int bid) {
		
		bookingsRepository.deleteById(bid);
		
		
	}

}
