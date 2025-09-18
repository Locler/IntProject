package com.intproject.dto;

import lombok.Data;
import java.util.List;

@Data
public class DriverDto {

    private Long id;

    private String name;

    private String email;

    private String phone;

    private List<CarDto> cars;
}
