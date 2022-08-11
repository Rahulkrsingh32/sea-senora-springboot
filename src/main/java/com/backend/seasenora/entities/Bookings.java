package com.backend.seasenora.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Bookings {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, updatable = false)
	private Integer id;
	
	private Date bookingDate;
	private Date bookedFromDate;
	private Date bookedToDate;
	private Integer boatId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonIgnore
	private  Customer customer;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonIgnore
	private  Boats boats;
	
	public Bookings() {}
	
	
	public Bookings(Date bookingDate, Date bookedFromDate, Date bookedToDate, Integer boatId) {
		super();
		this.bookingDate = bookingDate;
		this.bookedFromDate = bookedFromDate;
		this.bookedToDate = bookedToDate;
		this.boatId = boatId;
	}
	
	public Boats getBoats() {
		return boats;
	}

	public void setBoats(Boats boats) {
		this.boats = boats;
	}

	
	public Customer getCustomer() {
		return customer;
	}


	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}
	public Date getBookedFromDate() {
		return bookedFromDate;
	}
	public void setBookedFromDate(Date bookedFromDate) {
		this.bookedFromDate = bookedFromDate;
	}
	public Date getBookedToDate() {
		return bookedToDate;
	}
	public void setBookedToDate(Date bookedToDate) {
		this.bookedToDate = bookedToDate;
	}
	public Integer getBoatId() {
		return boatId;
	}
	public void setBoatId(Integer boatId) {
		this.boatId = boatId;
	}
	
	
	
}
