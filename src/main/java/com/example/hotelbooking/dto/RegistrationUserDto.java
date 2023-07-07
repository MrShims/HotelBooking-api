package com.example.hotelbooking.dto;


import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDate;

@Data
public class RegistrationUserDto {


    private String username;

    private String password;

    private String confirmPassword;

    private String email;

    private String birthDate;

    private String phone;


}
