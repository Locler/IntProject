package com.intproject.mappers;

import com.intproject.dto.TripDto;
import com.intproject.entities.Trip;
import com.intproject.entities.Driver;
import com.intproject.entities.Passenger;
import com.intproject.enums.TripStatus;
import com.intproject.repositories.DriverRep;
import com.intproject.repositories.PassengerRep;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class TripMapper {

    protected DriverRep driverRepository;

    protected PassengerRep passengerRepository;

    @Autowired
    public TripMapper(DriverRep driverRepository, PassengerRep passengerRepository) {
        this.driverRepository = driverRepository;
        this.passengerRepository = passengerRepository;
    }

    @Mapping(target = "driverId", source = "driver.id")
    @Mapping(target = "passengerId", source = "passenger.id")
    @Mapping(target = "status", expression = "java(entity.getStatus() != null ? entity.getStatus().name() : null)")
    public abstract TripDto toDto(Trip entity);

    @Mapping(target = "driver", source = "driverId", qualifiedByName = "idToDriver")
    @Mapping(target = "passenger", source = "passengerId", qualifiedByName = "idToPassenger")
    @Mapping(target = "status", expression = "java(dto.getStatus() != null ? com.intproject.enums.TripStatus.valueOf(dto.getStatus()) : null)")
    public abstract Trip toEntity(TripDto dto);

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
