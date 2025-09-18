package com.intproject.controllers;

import com.intproject.dto.CarDto;
import com.intproject.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService service;

    @Autowired
    public CarController(CarService service) {
        this.service = service;
    }

    @GetMapping
    public List<CarDto> getAllCars() {
        return service.getAllCars();
    }

    @GetMapping("/{id}")
    public CarDto getCarById(@PathVariable Long id) {
        return service.getCarById(id);
    }

    @PostMapping
    public CarDto createCar(@RequestBody CarDto dto) {
        return service.createCar(dto);
    }

    @PutMapping("/{id}")
    public CarDto updateCar(@PathVariable Long id, @RequestBody CarDto dto) {
        return service.updateCar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable Long id) {
        service.deleteCar(id);
    }

}