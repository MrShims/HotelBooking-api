package com.example.hotelbooking.dto;


import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;

/**
 * DTO для создания номера
 */
@Data
public class CreateRoomDto {


    /**
     * Тип номера
     */
    private String type;

    /**
     * Цена номера
     */
    private BigDecimal price;

    /**
     * Площадь номера
     */
    private double area;

    /**
     * Вместительность номера
     */
    private int capacity;


}
