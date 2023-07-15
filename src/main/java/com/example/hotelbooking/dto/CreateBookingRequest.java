package com.example.hotelbooking.dto;


import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class CreateBookingRequest {

        @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}",
                message = "Дата должна быть в формате yyyy-MM-dd")
        private String startDate;

        @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}",
                message = "Дата должна быть в формате yyyy-MM-dd")
        private String endDate;


        private Long id;



}
