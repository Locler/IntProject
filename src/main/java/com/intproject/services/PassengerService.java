package com.intproject.services;

import com.intproject.dto.PassengerDto;
import com.intproject.entities.Passenger;
import com.intproject.mappers.PassengerMapper;
import com.intproject.repositories.PassengerRep;
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
        return passengerMapper.toDtoList(passengerRep.findAll());
    }

    public PassengerDto getPassengerById(Long id) {
        return passengerMapper.toDto(passengerRep.findById(id)
                .filter(passenger -> !passenger.getDeleted())
                .orElseThrow(() -> new EntityNotFoundException("Passenger not found with id " + id)));
    }

    public PassengerDto createPassenger(PassengerDto dto) {
        return passengerMapper.toDto(passengerRep.save(passengerMapper.toEntity(dto)));
    }

    public PassengerDto updatePassenger(Long id, PassengerDto updatedPassenger) {
        Passenger passenger = passengerRep.findById(id).orElseThrow(() -> new EntityNotFoundException("Passenger not found with id " + id));
        passenger.setName(updatedPassenger.getName());
        passenger.setEmail(updatedPassenger.getEmail());
        passenger.setPhone(updatedPassenger.getPhone());
        return passengerMapper.toDto(passengerRep.save(passenger));
    }

    public void softDeletePassenger(Long id) {
        Passenger passenger = passengerRep.findById(id).orElseThrow(()-> new EntityNotFoundException("Passenger not found with id " + id));
        passenger.setDeleted(true);
        passengerRep.save(passenger);
    }

}
