package com.intproject.services;

import com.intproject.dto.CarDto;
import com.intproject.entities.Car;
import com.intproject.mappers.CarMapper;
import com.intproject.repositories.CarRep;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private final CarRep repository;
    private final CarMapper mapper;

    @Autowired
    public CarService(CarRep repository, CarMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<CarDto> getAllCars() {
        return mapper.toDtoList(repository.findAll());
    }

    public CarDto getCarById(Long id) {
        Car car = repository.findById(id).orElseThrow(()-> new EntityNotFoundException("Car not found with id " + id));
        return mapper.toDto(car);
    }

    public CarDto createCar(CarDto dto) {
        Car entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    public CarDto updateCar(Long id, CarDto dto) {
        Car car = repository.findById(id).orElseThrow(()-> new EntityNotFoundException("Car not found with id " + id));
        car.setBrand(dto.getBrand());
        car.setColor(dto.getColor());
        car.setPlateNumber(dto.getPlateNumber());
        return mapper.toDto(repository.save(car));
    }

    public void deleteCar(Long id) {
        repository.deleteById(id);
    }
}
