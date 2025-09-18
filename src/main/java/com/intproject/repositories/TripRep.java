package com.intproject.repositories;

import com.intproject.entities.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRep extends JpaRepository<Trip, Long> {
}
