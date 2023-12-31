package com.example.hotelbooking.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

/**
 * DTO для изменения личных данных пользователя
 */
@Data
public class UserDetailsRequest {

    /**
     * Почта пользователя
     */
    @NotBlank(message = "Почта не может быть пустым")
    private String email;
    /**
     * Дата рождения пользователя
     */
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}",
            message = "Дата должна быть в формате yyyy-MM-dd")
    private String birthDate;

    /**
     * Номер телефона пользователя
     */
    @NotBlank(message = "Телефон не может быть пустым")
    private String phone;


}
