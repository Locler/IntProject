package com.intproject.controllers;

import com.intproject.dto.PassengerDto;
import com.intproject.services.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/passengers")
public class PassengerController {

    private final PassengerService passengerService;

    @Autowired
    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @GetMapping
    public List<PassengerDto> getAllPassengers() {
        return passengerService.getAllPassengers();
    }

    @GetMapping("/{id}")
    public PassengerDto getPassengerById(@PathVariable Long id) {
        return passengerService.getPassengerById(id);
    }

    @PostMapping
    public PassengerDto createPassenger(@RequestBody PassengerDto passengerDto) {
        return passengerService.createPassenger(passengerDto);
    }

    @PutMapping({"/{id}"})
    public PassengerDto updatePassenger(@PathVariable Long id,@RequestBody PassengerDto passengerDto) {
        return passengerService.updatePassenger(id, passengerDto);
    }

    @DeleteMapping("/{id}")
    public void deletePassenger(@PathVariable Long id) {
        passengerService.softDeletePassenger(id);
    }
}
