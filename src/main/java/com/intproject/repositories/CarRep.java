package com.intproject.repositories;

import com.intproject.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRep extends JpaRepository<Car, Long> {

    List<Car> findAllByDriverId(Long driverId);

}
