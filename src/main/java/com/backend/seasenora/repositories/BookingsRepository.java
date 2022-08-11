package com.backend.seasenora.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.seasenora.entities.Bookings;

@Repository
@Transactional
public interface BookingsRepository extends JpaRepository<Bookings, Integer> {

}
