package com.intproject.repositories;

import com.intproject.entities.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassengerRep extends JpaRepository<Passenger, Long> {

    List<Passenger> findPassengersByDeletedFalse();

    boolean existsPassengerByEmail(String email);
}
