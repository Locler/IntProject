package com.intproject.repositories;

import com.intproject.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRep extends JpaRepository<Car, Long> {
}
