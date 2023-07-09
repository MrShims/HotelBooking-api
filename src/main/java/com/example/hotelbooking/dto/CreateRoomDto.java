package com.example.hotelbooking.dto;


import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;

@Data
public class CreateRoomDto {



    private String type;

    private BigDecimal price;

    private double area;

    private int capacity;



}
