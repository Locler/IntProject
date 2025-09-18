package com.intproject.dto;

import lombok.Data;

@Data
public class RatingDto {

    private Long id;

    private Long tripId;

    private Long driverId;

    private Long passengerId;

    private Integer score;

    private String comment;
}
