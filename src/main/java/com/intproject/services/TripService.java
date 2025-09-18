package com.intproject.services;

import com.intproject.dto.TripDto;
import com.intproject.entities.Trip;
import com.intproject.enums.TripStatus;
import com.intproject.mappers.TripMapper;
import com.intproject.repositories.TripRep;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripService {

    private final TripRep tripRep;
    private final TripMapper tripMapper;

    @Autowired
    public TripService(TripRep tripRep, TripMapper tripMapper) {
        this.tripRep = tripRep;
        this.tripMapper = tripMapper;
    }

    public List<TripDto> getAllTrips() {
        return tripRep.findAll()
                .stream()
                .map(tripMapper::toDto)
                .toList();
    }

    public TripDto getTripById(Long id) {
        Trip trip = tripRep.findById(id)
                .orElseThrow(() -> new RuntimeException("Trip not found"+"id="+id));
        return tripMapper.toDto(trip);
    }

    public TripDto createTrip(TripDto dto) {
        Trip entity = tripMapper.toEntity(dto);
        Trip saved = tripRep.save(entity);
        return tripMapper.toDto(saved);
    }

    public TripDto updateTrip(Long id, TripDto dto) {
        Trip trip = tripRep.findById(id).orElseThrow(()-> new RuntimeException("Trip not found"+"id="+id));
        trip.setFromAddress(dto.getFromAddress());
        trip.setToAddress(dto.getToAddress());
        trip.setPrice(dto.getPrice());
        trip.setStatus(dto.getStatus());
        return tripMapper.toDto(tripRep.save(trip));
    }

    public TripDto updateStatus(Long id, TripStatus status) {
        Trip trip = tripRep.findById(id)
                .orElseThrow(() -> new RuntimeException("Trip not found"+"id="+id));
        trip.setStatus(status);
        return tripMapper.toDto(tripRep.save(trip));
    }

    @Transactional
    public void deleteTrip(Long id) {
        Trip trip = tripRep.findById(id).orElseThrow(() -> new EntityNotFoundException("Trip not found"));
        // нельзя удалять завершённую поездку
        if (trip.getStatus() == TripStatus.COMPLETED) {
            throw new IllegalStateException("Cannot delete a completed trip");
        }
        trip.setStatus(TripStatus.CANCELED);
        tripRep.save(trip);
    }

    //Физическое удаление.
    @Transactional
    public void hardDelete(Long id) {
        if (!tripRep.existsById(id)) {
            throw new EntityNotFoundException("Trip not found");
        }
        tripRep.deleteById(id);
    }

}
