package com.intproject.repositories;

import com.intproject.entities.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRep extends JpaRepository<Driver, Long> {
    List<Driver> findDriversByDeletedFalse();
}
