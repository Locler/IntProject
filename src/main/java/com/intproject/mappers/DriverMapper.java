package com.intproject.mappers;

import com.intproject.dto.DriverDto;
import com.intproject.entities.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import java.util.List;

@Mapper(componentModel = "spring", uses = {CarMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DriverMapper {

    DriverDto toDto(Driver entity);

    Driver toEntity(DriverDto dto);

    List<DriverDto> toDtoList(List<Driver> entities);
}
