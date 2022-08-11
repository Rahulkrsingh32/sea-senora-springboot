package com.backend.seasenora.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Boats {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, updatable = false)
	private Integer id;
	
	@OneToMany(mappedBy="boats")
	private List<Bookings> bookings;
	
		
	private String boatName;
	private String imageUrl;
	private String fuelType;
	private int maxPassengers;
	private double ratePerDay;
	
	public List<Bookings> getBookings() {
		return bookings;
	}

	public void setBookings(List<Bookings> bookings) {
		this.bookings = bookings;
	}
	
	public Boats() {}

	public Boats(List<Bookings> bookings, String boatName, String imageUrl, String fuelType, int maxPassengers,
			double ratePerDay) {
		super();
		this.bookings = bookings;
		this.boatName = boatName;
		this.imageUrl = imageUrl;
		this.fuelType = fuelType;
		this.maxPassengers = maxPassengers;
		this.ratePerDay = ratePerDay;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBoatName() {
		return boatName;
	}

	public void setBoatName(String boatName) {
		this.boatName = boatName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getFuelType() {
		return fuelType;
	}

	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

	public int getMaxPassengers() {
		return maxPassengers;
	}

	public void setMaxPassengers(int maxPassengers) {
		this.maxPassengers = maxPassengers;
	}

	public double getRatePerDay() {
		return ratePerDay;
	}

	public void setRatePerDay(double ratePerDay) {
		this.ratePerDay = ratePerDay;
	}
	
	
	
}
