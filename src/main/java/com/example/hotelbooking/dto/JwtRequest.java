package com.example.hotelbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO для аутентификации пользователя
 */
@Data
@AllArgsConstructor
public class JwtRequest {
    /**
     * Логин пользователя
     */
    private String username;
    /**
     * Пароль пользователя
     */
    private String password;

}
