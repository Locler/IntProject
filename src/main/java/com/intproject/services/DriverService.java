package com.intproject.services;

import com.intproject.dto.DriverDto;
import com.intproject.entities.Driver;
import com.intproject.mappers.DriverMapper;
import com.intproject.repositories.DriverRep;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverService {
    private final DriverRep driverRep;
    private final DriverMapper driverMapper;

    @Autowired
    public DriverService(DriverRep driverRep, DriverMapper driverMapper) {
        this.driverRep = driverRep;
        this.driverMapper = driverMapper;
    }

    public List<DriverDto> getAllDrivers() {
        return driverMapper.toDtoList(driverRep.findDriversByDeletedFalse());
    }

    public DriverDto getDriverById(Long id) {
        return driverMapper.toDto(driverRep.findById(id)
                .filter(passenger -> !passenger.getDeleted())
                .orElseThrow(() -> new EntityNotFoundException("Driver not found with id " + id)));
    }

    public DriverDto createDriver(DriverDto dto) {
        if (driverRep.existsDriverByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already used: " + dto.getEmail());
        }
        return driverMapper.toDto(driverRep.save(driverMapper.toEntity(dto)));
    }

    public DriverDto updateDriver(Long id, DriverDto dto) {
        Driver driver = driverRep.findById(id)
                .filter(d -> !d.getDeleted())
                .orElseThrow(() -> new EntityNotFoundException("Driver not found"));

        driver.setName(dto.getName());
        driver.setPhone(dto.getPhone());

        if (!driver.getEmail().equals(dto.getEmail())
                && driverRep.existsDriverByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already used: " + dto.getEmail());
        }
        driver.setEmail(dto.getEmail());

        return driverMapper.toDto(driverRep.save(driver));
    }

    public void softDeleteDriver(Long id) {
        Driver driver = driverRep.findById(id)
                .filter(d -> !d.getDeleted())
                .orElseThrow(() -> new EntityNotFoundException("Driver not found"));
        driver.setDeleted(true);
        driverRep.save(driver);
    }
}
