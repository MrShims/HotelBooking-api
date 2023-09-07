package com.example.hotelbooking.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RoomFilterDto {


    private int capacity;

    private String startDate;

    private String endDate;


}
