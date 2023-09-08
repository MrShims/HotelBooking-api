package com.example.hotelbooking.service;


import com.example.hotelbooking.dto.JwtRequest;
import com.example.hotelbooking.dto.JwtResponse;
import com.example.hotelbooking.dto.RegistrationUserDto;
import com.example.hotelbooking.entity.User;
import com.example.hotelbooking.exceptions.AppError;
import com.example.hotelbooking.exceptions.AuthenticationException;
import com.example.hotelbooking.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Сервис для регистрации и аутентификации пользователей.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;

    private final JwtTokenUtils jwtTokenUtils;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;


    /**
     * Метод для аутентификации пользователя. В случае успеха возвращается JsonWebToken
     *
     * @param authRequest login and password
     * @return JWT token
     */
    public JwtResponse createAuthToken(JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Wrong password or username");
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);

        return new JwtResponse(token);
    }

    /**
     * Метод для регистрации нового пользователя. В случае успеха возвращается HttpStatus.Created. В случае ошибки возвращается HttpStatus.Bad_Request
     *
     * @param registrationUserDto User data
     * @return httpStatus
     */
    public ResponseEntity<?> createNewUser(RegistrationUserDto registrationUserDto) {

        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            throw new AuthenticationException("Пароли не совпадают");
        }
        if (userService.findByUserName(registrationUserDto.getUsername()).isPresent()) {
            throw new AuthenticationException("Пользователь с таким логином уже существует");
        }

        User user = new User();

        user.setUsername(registrationUserDto.getUsername());
        user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
        com.example.hotelbooking.entity.UserDetails detailsNewUser = new com.example.hotelbooking.entity.UserDetails();

        detailsNewUser.setPhone(registrationUserDto.getPhone());
        try {
            detailsNewUser.setBirthDate(LocalDate.parse(registrationUserDto.getBirthDate()));
        } catch (DateTimeParseException e) {
            throw new AuthenticationException("Неправильный формат даты рождения");
        }
        detailsNewUser.setEmail(registrationUserDto.getEmail());
        user.setUserDetails(detailsNewUser);


        userService.createNewUser(user);


        return new ResponseEntity<>(HttpStatus.CREATED);


    }


}
