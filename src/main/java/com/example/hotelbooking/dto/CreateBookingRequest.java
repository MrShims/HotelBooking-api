package com.example.hotelbooking.dto;


import lombok.Data;

import javax.validation.constraints.Pattern;

/**
 * DTO для бронирования номера
 */
@Data
public class CreateBookingRequest {

        /**
         * Дата начала бронирования
         */
        @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}",
                message = "Дата должна быть в формате yyyy-MM-dd")
        private String startDate;

        /**
         * Дата окончания бронирования
         */
        @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}",
                message = "Дата должна быть в формате yyyy-MM-dd")
        private String endDate;


        /**
         * id номера
         */
        private Long id;



}
