package com.intproject.controllers;

import com.intproject.dto.TripDto;
import com.intproject.enums.TripStatus;
import com.intproject.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trips")
public class TripController {

    private final TripService service;

    @Autowired
    public TripController(TripService service) {
        this.service = service;
    }

    @GetMapping
    public List<TripDto> getAllTrips() {
        return service.getAllTrips();
    }

    @GetMapping("/{id}")
    public TripDto getTripById(@PathVariable Long id) {
        return service.getTripById(id);
    }

    @PostMapping
    public TripDto createTrip(@RequestBody TripDto dto) {
        return service.createTrip(dto);
    }

    @PutMapping("/{id}")
    public TripDto updateTrip(@PathVariable Long id, @RequestBody TripDto dto) {
        return service.updateTrip(id, dto);
    }

    @PatchMapping("/{id}/status")
    public TripDto changeStatus(@PathVariable Long id, @RequestParam TripStatus status) {
        return service.updateStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public void deleteTrip(@PathVariable Long id) {
        service.deleteTrip(id);
    }

    @DeleteMapping("/{id}/hard")
    public void hardDelete(@PathVariable Long id) {
        service.hardDelete(id);
    }
}