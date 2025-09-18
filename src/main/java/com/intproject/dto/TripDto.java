package com.intproject.dto;

import com.intproject.enums.TripStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TripDto {

    private Long id;

    private Long driverId;

    private Long passengerId;

    private String fromAddress;

    private String toAddress;

    private TripStatus status;

    private LocalDateTime requestedAt;

    private BigDecimal price;
}