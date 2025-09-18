package com.intproject.services;

import com.intproject.dto.RatingDto;
import com.intproject.entities.Rating;
import com.intproject.mappers.RatingMapper;
import com.intproject.repositories.RatingRep;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {
    private final RatingRep ratingRep;
    private final RatingMapper ratingMapper;

    @Autowired
    public RatingService(RatingRep repository, RatingMapper mapper) {
        this.ratingRep = repository;
        this.ratingMapper = mapper;
    }

    public List<RatingDto> getAllRatings() {
        return ratingRep.findAll().stream().map(ratingMapper::toDto).toList();
    }

    public RatingDto getRatingById(Long id) {
        Rating rating = ratingRep.findById(id).orElseThrow(()-> new EntityNotFoundException("Rating not found with id " + id));
        return ratingMapper.toDto(rating);
    }

    public RatingDto creatRating(RatingDto dto) {
        return ratingMapper.toDto(ratingRep.save(ratingMapper.toEntity(dto)));
    }

    public RatingDto updateRating(Long id, RatingDto dto) {
        Rating rating = ratingRep.findById(id).orElseThrow(()-> new EntityNotFoundException("Rating not found with id " + id));
        rating.setScore(dto.getScore());
        rating.setComment(dto.getComment());
        return ratingMapper.toDto(ratingRep.save(rating));
    }

    public void deleteRating(Long id) {
        ratingRep.deleteById(id);
    }
}
