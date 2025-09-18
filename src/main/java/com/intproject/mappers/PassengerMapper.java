package com.intproject.mappers;


import com.intproject.dto.PassengerDto;
import com.intproject.entities.Passenger;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PassengerMapper {

    PassengerDto toDto(Passenger entity);

    Passenger toEntity(PassengerDto dto);

    List<PassengerDto> toDtoList(List<Passenger> entities);
}