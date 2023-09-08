package com.example.hotelbooking.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * DTO для фильтрации свободных номеров
 */
@Data
public class RoomFilterDto {


    /**
     * Вместительность номера
     */
    private int capacity;

    /**
     * Дата начала бронирования
     */
    private String startDate;

    /**
     * Дата окончания бронирования
     */
    private String endDate;


}
