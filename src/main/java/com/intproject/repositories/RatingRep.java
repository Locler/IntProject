package com.intproject.repositories;

import com.intproject.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRep extends JpaRepository<Rating, Long> {

    List<Rating> findAllByDriverId(Long driverId);

    List<Rating> findAllByPassengerId(Long passengerId);

    List<Rating> findAllByTripId(Long tripId);

}
