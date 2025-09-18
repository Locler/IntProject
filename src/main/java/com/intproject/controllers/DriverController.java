package com.intproject.controllers;

import com.intproject.dto.DriverDto;
import com.intproject.services.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drivers")
public class DriverController {

    private final DriverService service;

    @Autowired
    public DriverController(DriverService service) {
        this.service = service;
    }

    @GetMapping
    public List<DriverDto> getAllDrivers() {
        return service.getAllDrivers();
    }

    @GetMapping("/{id}")
    public DriverDto getDriverById(@PathVariable Long id) {
        return service.getDriverById(id);
    }

    @PostMapping
    public DriverDto createDriver(@RequestBody DriverDto dto) {
        return service.createDriver(dto);
    }

    @PutMapping("/{id}")
    public DriverDto updateDriver(@PathVariable Long id, @RequestBody DriverDto dto) {
        return service.updateDriver(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.softDeleteDriver(id);
    }
}