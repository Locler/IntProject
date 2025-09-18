package com.intproject.mappers;

import com.intproject.dto.CarDto;
import com.intproject.entities.Car;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)//игнорирование несоотв полей
public interface CarMapper {

    CarDto toDto(Car entity);

    Car toEntity(CarDto dto);

    List<CarDto> toDtoList(List<Car> entities);
}
