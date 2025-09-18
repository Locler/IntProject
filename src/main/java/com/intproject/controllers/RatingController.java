package com.intproject.controllers;

import com.intproject.dto.RatingDto;
import com.intproject.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    private final RatingService service;

    @Autowired
    public RatingController(RatingService service) {
        this.service = service;
    }

    @GetMapping
    public List<RatingDto> getAllRatings() {
        return service.getAllRatings();
    }

    @GetMapping("/{id}")
    public RatingDto getRatingById(@PathVariable Long id) {
        return service.getRatingById(id);
    }

    @PostMapping
    public RatingDto createRating(@RequestBody RatingDto dto) {
        return service.createRating(dto);
    }

    @PutMapping("/{id}")
    public RatingDto updateRating(@PathVariable Long id, @RequestBody RatingDto dto) {
        return service.updateRating(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteRating(@PathVariable Long id) {
        service.deleteRating(id);
    }
}
