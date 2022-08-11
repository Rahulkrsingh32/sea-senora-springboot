package com.backend.seasenora.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.seasenora.entities.Boats;
import com.backend.seasenora.exceptions.BoatNotFoundException;
import com.backend.seasenora.repositories.BoatsRepository;

@Service
@Transactional
public class BoatsService {
	
	@Autowired
	private BoatsRepository boatsRepository;
	
	public void updateBoat(int id, Boats boat) throws BoatNotFoundException {
		Boats boat_temp = boatsRepository.findById(id).orElseThrow(()-> new BoatNotFoundException("boat not found"));
		boat_temp.setBoatName(boat.getBoatName());
		boat_temp.setImageUrl(boat.getImageUrl());
		boat_temp.setFuelType(boat.getFuelType());
		boat_temp.setMaxPassengers(boat.getMaxPassengers());
		boat_temp.setRatePerDay(boat.getRatePerDay());
		boatsRepository.save(boat_temp);
	}

}
