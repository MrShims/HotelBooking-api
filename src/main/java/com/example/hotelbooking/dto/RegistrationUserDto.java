package com.example.hotelbooking.dto;


import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
public class RegistrationUserDto {


    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String username;

    @NotEmpty(message = "Пароль не может быть пустым")
    private String password;
    @NotEmpty(message = "Подтверждение пароля не может быть пустым")
    private String confirmPassword;

    @NotBlank
    @Email(message = "Неверный формат адреса электронной почты")
    private String email;


    @NotBlank
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Неверный формат даты рождения. Ожидается формат dd-MM-yyyy")
    private String birthDate;


    @Pattern(regexp = "\\d-[0-9]{3}-[0-9]{3}-[0-9]{2}-[0-9]{2}", message = "Неверный формат номера телефона")
    private String phone;


}
