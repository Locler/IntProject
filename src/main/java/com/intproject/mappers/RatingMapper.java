package com.intproject.mappers;

import com.intproject.dto.RatingDto;
import com.intproject.entities.Rating;
import com.intproject.entities.Trip;
import com.intproject.entities.Driver;
import com.intproject.entities.Passenger;
import com.intproject.repositories.TripRep;
import com.intproject.repositories.DriverRep;
import com.intproject.repositories.PassengerRep;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class RatingMapper {

    protected TripRep tripRepository;

    protected DriverRep driverRepository;

    protected PassengerRep passengerRepository;

    @Autowired
    public RatingMapper(TripRep tripRepository, DriverRep driverRepository, PassengerRep passengerRepository) {
        this.tripRepository = tripRepository;
        this.driverRepository = driverRepository;
        this.passengerRepository = passengerRepository;
    }

    @Mapping(target = "tripId", source = "trip.id")
    @Mapping(target = "driverId", source = "driver.id")
    @Mapping(target = "passengerId", source = "passenger.id")
    public abstract RatingDto toDto(Rating entity);

    @Mapping(target = "trip", source = "tripId", qualifiedByName = "idToTrip")
    @Mapping(target = "driver", source = "driverId", qualifiedByName = "idToDriver")
    @Mapping(target = "passenger", source = "passengerId", qualifiedByName = "idToPassenger")
    public abstract Rating toEntity(RatingDto dto);

    @Named("idToTrip")
    protected Trip idToTrip(Long id) {
        if (id == null) return null;
        return tripRepository.getReferenceById(id);
    }

    @Named("idToDriver")
    protected Driver idToDriver(Long id) {
        if (id == null) return null;
        return driverRepository.getReferenceById(id);
    }

    @Named("idToPassenger")
    protected Passenger idToPassenger(Long id) {
        if (id == null) return null;
        return passengerRepository.getReferenceById(id);
    }
}
