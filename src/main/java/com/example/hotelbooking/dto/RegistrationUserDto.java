package com.example.hotelbooking.dto;


import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

/**
 * DTO для регистрации нового пользователя
 */
@Data
public class RegistrationUserDto {


    /**
     * Логин пользователя
     */
    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String username;

    /**
     * Пароль пользователя
     */
    @NotEmpty(message = "Пароль не может быть пустым")
    private String password;
    /**
     * Подтвержденный пароль пользователя
     */
    @NotEmpty(message = "Подтверждение пароля не может быть пустым")
    private String confirmPassword;

    /**
     * Почта пользователя
     */
    @NotBlank
    @Email(message = "Неверный формат адреса электронной почты")
    private String email;


    /**
     * Дата рождения пользователя
     */
    @NotBlank
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Неверный формат даты рождения. Ожидается формат dd-MM-yyyy")
    private String birthDate;


    /**
     * Номер телефона пользователя
     */
    private String phone;


}
