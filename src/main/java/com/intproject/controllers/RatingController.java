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
    public List<RatingDto> getAll() {
        return service.getAllRatings();
    }

    @GetMapping("/{id}")
    public RatingDto getById(@PathVariable Long id) {
        return service.getRatingById(id);
    }

    @PostMapping
    public RatingDto create(@RequestBody RatingDto dto) {
        return service.createRating(dto);
    }

    @PutMapping("/{id}")
    public RatingDto update(@PathVariable Long id, @RequestBody RatingDto dto) {
        return service.updateRating(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteRating(id);
    }
}
