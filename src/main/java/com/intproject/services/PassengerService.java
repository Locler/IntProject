package com.intproject.services;

import com.intproject.dto.PassengerDto;
import com.intproject.entities.Passenger;
import com.intproject.mappers.PassengerMapper;
import com.intproject.repositories.PassengerRep;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerService {

    private final PassengerRep passengerRep;
    private final PassengerMapper passengerMapper;

    @Autowired
    public PassengerService(PassengerRep passengerRep, PassengerMapper passengerMapper) {
        this.passengerRep = passengerRep;
        this.passengerMapper = passengerMapper;
    }

    public List<PassengerDto> getAllPassengers() {
        return passengerMapper.toDtoList(passengerRep.findPassengersByDeletedFalse());
    }

    public PassengerDto getPassengerById(Long id) {
        return passengerMapper.toDto(passengerRep.findById(id)
                .filter(passenger -> !passenger.getDeleted())
                .orElseThrow(() -> new EntityNotFoundException("Passenger not found with id " + id)));
    }

    public PassengerDto createPassenger(PassengerDto dto) {
        if(passengerRep.existsPassengerByEmail(dto.getEmail())) {
            throw new EntityExistsException("Passenger with email " + dto.getEmail() + " already exists");
        }
        return passengerMapper.toDto(passengerRep.save(passengerMapper.toEntity(dto)));
    }

    public PassengerDto updatePassenger(Long id, PassengerDto updatedPassenger) {
        Passenger passenger = passengerRep.findById(id)
                .filter(p -> !p.getDeleted())
                .orElseThrow(() -> new EntityNotFoundException("Passenger not found"));

        passenger.setName(updatedPassenger.getName());
        passenger.setPhone(updatedPassenger.getPhone());

        if (!passenger.getEmail().equals(updatedPassenger.getEmail())
                && passengerRep.existsPassengerByEmail(updatedPassenger.getEmail())) {
            throw new IllegalArgumentException("Email already used: " + updatedPassenger.getEmail());
        }
        passenger.setEmail(updatedPassenger.getEmail());

        return passengerMapper.toDto(passengerRep.save(passenger));
    }

    public void softDeletePassenger(Long id) {
        Passenger passenger = passengerRep.findById(id)
                .filter(p -> !p.getDeleted())
                .orElseThrow(() -> new EntityNotFoundException("Passenger not found"));
        passenger.setDeleted(true);
        passengerRep.save(passenger);
    }

}
