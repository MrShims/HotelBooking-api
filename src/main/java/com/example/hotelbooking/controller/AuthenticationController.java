package com.example.hotelbooking.controller;

import com.example.hotelbooking.dto.JwtRequest;
import com.example.hotelbooking.dto.JwtResponse;
import com.example.hotelbooking.dto.RegistrationUserDto;
import com.example.hotelbooking.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер для аутентификации и регистрации пользователей
 */
@RestController
@RequiredArgsConstructor
public class AuthenticationController {


    private final AuthService authService;


    /**
     * Обрабатывает HTTP-запрос на аутентификацию пользователя.
     * @param authRequest бъект с данными для аутентификации пользователя.
     * @return ResponseEntity с токеном аутентификации или сообщением об ошибке аутентификации.
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody JwtRequest authRequest) {

        JwtResponse authToken = authService.createAuthToken(authRequest);
        return new ResponseEntity<>(authToken,HttpStatus.OK);
    }

    /**
     * Создает нового пользователя с использованием предоставленных данных регистрации.
     * @param registrationUserDto Объект с данными для регистрации нового пользователя.
     * @param bindingResult Результаты валидации данных регистрации.
     * @return ResponseEntity с информацией о созданном пользователе или сообщением об ошибках валидации.
     */
    @PostMapping("/register")
    public ResponseEntity<?> createNewUser(@Valid @RequestBody RegistrationUserDto registrationUserDto, BindingResult bindingResult) {


        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult
                    .getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());


            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        ResponseEntity<?> newUser = authService.createNewUser(registrationUserDto);

        return newUser;


    }


}
