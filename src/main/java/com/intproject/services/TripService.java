package com.intproject.services;

import com.intproject.dto.TripDto;
import com.intproject.entities.Driver;
import com.intproject.entities.Passenger;
import com.intproject.entities.Trip;
import com.intproject.enums.TripStatus;
import com.intproject.mappers.TripMapper;
import com.intproject.repositories.DriverRep;
import com.intproject.repositories.PassengerRep;
import com.intproject.repositories.TripRep;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripService {

    private final TripRep repository;
    private final DriverRep driverRepository;
    private final PassengerRep passengerRepository;
    private final TripMapper mapper;

    @Autowired
    public TripService(TripRep repository, DriverRep driverRepository, PassengerRep passengerRepository, TripMapper mapper) {
        this.repository = repository;
        this.driverRepository = driverRepository;
        this.passengerRepository = passengerRepository;
        this.mapper = mapper;
    }

    public List<TripDto> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public TripDto getTripById(Long id) {
        Trip trip = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Trip not found"));
        return mapper.toDto(trip);
    }

    public TripDto createTrip(TripDto dto) {
        Driver driver = driverRepository.findById(dto.getDriverId())
                .orElseThrow(() -> new EntityNotFoundException("Driver not found"));
        Passenger passenger = passengerRepository.findById(dto.getPassengerId())
                .orElseThrow(() -> new EntityNotFoundException("Passenger not found"));

        Trip trip = mapper.toEntity(dto);

        trip.setDriver(driver);
        trip.setPassenger(passenger);
        trip.setStatus(TripStatus.CREATED);

        return mapper.toDto(repository.save(trip));
    }

    public TripDto updateTrip(Long id, TripDto dto) {
        Trip trip = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Trip not found"));

        trip.setFromAddress(dto.getFromAddress());
        trip.setToAddress(dto.getToAddress());
        trip.setPrice(dto.getPrice());

        return mapper.toDto(repository.save(trip));
    }

    public TripDto updateStatus(Long id, TripStatus status) {
        Trip trip = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Trip not found"));

        TripStatus current = trip.getStatus();
        if (current == TripStatus.COMPLETED || current == TripStatus.CANCELLED) {
            throw new IllegalStateException("Trip already finished");
        }

        trip.setStatus(status);
        return mapper.toDto(repository.save(trip));
    }

    @Transactional
    public void deleteTrip(Long id) {
        Trip trip = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Trip not found"));
        // нельзя удалять завершённую поездку
        if (trip.getStatus() == TripStatus.COMPLETED) {
            throw new IllegalStateException("Cannot delete a completed trip");
        }
        trip.setStatus(TripStatus.CANCELLED);
        repository.save(trip);
    }

    //Физическое удаление.
    @Transactional
    public void hardDelete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Trip not found");
        }
        repository.deleteById(id);
    }

}
