package com.example.hotelbooking.dto;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO информации о пользователя
 */
@Data
public class UserResponseDto {

    /**
     * Логин пользователя
     */
    private String username;

    /**
     * Почта пользователя
     */
    private String email;

    /**
     * Дата рождения пользователя
     */
    private LocalDate BirthDate;

    /**
     * Телефон пользователя
     */
    private String phone;


}
