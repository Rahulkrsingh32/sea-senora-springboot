package com.backend.seasenora.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.backend.seasenora.entities.Boats;

import com.backend.seasenora.exceptions.BoatNotFoundException;
import com.backend.seasenora.exceptions.UserNotFoundException;
import com.backend.seasenora.repositories.BoatsRepository;
import com.backend.seasenora.services.BoatsService;

@RestController
public class BoatsController {
	
	@Autowired
	private BoatsRepository boatsRepository;
	
	@Autowired
	private BoatsService boatsService;
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/boats")
	public List<Boats> retriveAllBoats() {
		return boatsRepository.findAll();
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/boats/{id}")
	public Optional<Boats> retriveUser(@PathVariable int id) {
		
		Optional<Boats> boatOptional = boatsRepository.findById(id);
		
		if(!boatOptional.isPresent())
			throw new UserNotFoundException("id-"+ id);
		Boats boat = boatOptional.get();
		List<Boats> boatList=new ArrayList<Boats>();
		boatList.add(boat);
		
		return boatOptional; 
	}
	
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/admin/addboat")
	public ResponseEntity<Object> addBoat(@Valid @RequestBody Boats boat) { //NOSONAR
		Boats savedBoat = boatsRepository.save(boat);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedBoat.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/admin/updateboat/{id}")
	public ResponseEntity<Object> updateBoat(@PathVariable int id, @RequestBody Boats boats) throws BoatNotFoundException { //NOSONAR
		boatsService.updateBoat(id, boats);
		return new ResponseEntity<Object>(HttpStatus.ACCEPTED);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/admin/deleteboat/{id}")
	public void deleteBoat(@PathVariable int id) {
		
		boatsRepository.deleteById(id);
		
		
	}
	
	

}
