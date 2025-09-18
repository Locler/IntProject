package com.intproject.services;

import com.intproject.dto.RatingDto;
import com.intproject.entities.Driver;
import com.intproject.entities.Passenger;
import com.intproject.entities.Rating;
import com.intproject.entities.Trip;
import com.intproject.mappers.RatingMapper;
import com.intproject.repositories.DriverRep;
import com.intproject.repositories.PassengerRep;
import com.intproject.repositories.RatingRep;
import com.intproject.repositories.TripRep;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    private final RatingRep repository;
    private final TripRep tripRepository;
    private final PassengerRep passengerRepository;
    private final DriverRep driverRepository;
    private final RatingMapper mapper;

    @Autowired
    public RatingService(RatingRep repository, TripRep tripRepository, PassengerRep passengerRepository, DriverRep driverRepository, RatingMapper mapper) {
        this.repository = repository;
        this.tripRepository = tripRepository;
        this.passengerRepository = passengerRepository;
        this.driverRepository = driverRepository;
        this.mapper = mapper;
    }

    public List<RatingDto> getAllRatings() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public RatingDto getRatingById(Long id) {
        Rating rating = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rating not found"));
        return mapper.toDto(rating);
    }

    public RatingDto createRating(RatingDto dto) {
        Trip trip = tripRepository.findById(dto.getTripId())
                .orElseThrow(() -> new EntityNotFoundException("Trip not found"));

        Rating rating = mapper.toEntity(dto);
        rating.setTrip(trip);

        if (dto.getPassengerId() != null) {
            Passenger passenger = passengerRepository.findById(dto.getPassengerId())
                    .orElseThrow(() -> new EntityNotFoundException("Passenger not found"));
            rating.setPassenger(passenger);
        }

        if (dto.getDriverId() != null) {
            Driver driver = driverRepository.findById(dto.getDriverId())
                    .orElseThrow(() -> new EntityNotFoundException("Driver not found"));
            rating.setDriver(driver);
        }

        return mapper.toDto(repository.save(rating));
    }

    public RatingDto updateRating(Long id, RatingDto dto) {
        Rating rating = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rating not found"));

        rating.setScore(dto.getScore());
        rating.setComment(dto.getComment());

        return mapper.toDto(repository.save(rating));
    }

    public void deleteRating(Long id) {
        Rating rating = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rating not found"));
        repository.delete(rating);
    }

    public List<RatingDto> getByDriver(Long driverId) {
        return repository.findAllByDriverId(driverId)
                .stream().map(mapper::toDto).toList();
    }

    public List<RatingDto> getByPassenger(Long passengerId) {
        return repository.findAllByPassengerId(passengerId)
                .stream().map(mapper::toDto).toList();
    }

    public List<RatingDto> getByTrip(Long tripId) {
        return repository.findAllByTripId(tripId)
                .stream().map(mapper::toDto).toList();
    }
}
