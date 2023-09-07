package com.example.hotelbooking.dto;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserResponseDto {

    private String username;

    private String email;

    private LocalDate BirthDate;

    private String phone;


}
