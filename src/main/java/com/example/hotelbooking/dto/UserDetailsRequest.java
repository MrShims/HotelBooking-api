package com.example.hotelbooking.dto;

import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDate;

@Data
public class UserDetailsRequest {

    private String email;

    private LocalDate BirthDate;

    private String phone;


}
