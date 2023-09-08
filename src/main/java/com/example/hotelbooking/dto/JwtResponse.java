package com.example.hotelbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO для возвращение JsonWebToken в случаи успешной аутентификации
 */
@Data
@AllArgsConstructor
public class JwtResponse {
    /**
     * JsonWebToken
     */
    private String token;
}
