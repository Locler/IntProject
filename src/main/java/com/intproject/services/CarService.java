package com.intproject.services;

import com.intproject.dto.CarDto;
import com.intproject.entities.Car;
import com.intproject.entities.Driver;
import com.intproject.mappers.CarMapper;
import com.intproject.mappers.DriverMapper;
import com.intproject.repositories.CarRep;
import com.intproject.repositories.DriverRep;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private final CarRep repository;
    private final DriverRep driverRep;
    private final CarMapper mapper;

    @Autowired
    public CarService(CarRep repository, CarMapper mapper, DriverRep driverRep) {
        this.repository = repository;
        this.mapper = mapper;
        this.driverRep = driverRep;
    }

    public List<CarDto> getAllCars() {
        return mapper.toDtoList(repository.findAll());
    }

    public CarDto getCarById(Long id) {
        Car car = repository.findById(id).orElseThrow(()-> new EntityNotFoundException("Car not found with id " + id));
        return mapper.toDto(car);
    }

    public CarDto createCar(CarDto dto) {
        Driver driver = driverRep.findById(dto.getDriverId())
                .orElseThrow(() -> new EntityNotFoundException("Driver not found"));
        Car entity = mapper.toEntity(dto);
        entity.setDriver(driver);
        return mapper.toDto(repository.save(entity));
    }

    public CarDto updateCar(Long id, CarDto dto) {
        Car car = repository.findById(id).orElseThrow(()-> new EntityNotFoundException("Car not found with id " + id));
        car.setBrand(dto.getBrand());
        car.setColor(dto.getColor());
        car.setPlateNumber(dto.getPlateNumber());

        if (dto.getDriverId() != null) {
            Driver driver = driverRep.findById(dto.getDriverId())
                    .orElseThrow(() -> new EntityNotFoundException("Driver not found"));
            car.setDriver(driver);
        }
        return mapper.toDto(repository.save(car));
    }

    public void deleteCar(Long id) {
        Car car = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car not found"));
        repository.delete(car);
    }
}
