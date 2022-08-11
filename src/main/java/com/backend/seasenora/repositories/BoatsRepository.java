package com.backend.seasenora.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.seasenora.entities.Boats;

@Repository
@Transactional
public interface BoatsRepository extends JpaRepository<Boats, Integer> {

}
