package com.example.hotelbooking.service;


import com.example.hotelbooking.dto.JwtRequest;
import com.example.hotelbooking.dto.JwtResponse;
import com.example.hotelbooking.dto.RegistrationUserDto;
import com.example.hotelbooking.entity.User;
import com.example.hotelbooking.exceptions.AppError;
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

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;

    private final JwtTokenUtils jwtTokenUtils;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;



    public ResponseEntity<?> createAuthToken(JwtRequest authRequest)
    {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Неправильный логин или пароль"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);

        return new ResponseEntity(new JwtResponse(token), HttpStatus.OK);
    }

    public ResponseEntity<?> createNewUser( RegistrationUserDto registrationUserDto) {

        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пароли не совпадают"), HttpStatus.BAD_REQUEST);
        }
        if (userService.findByUserName(registrationUserDto.getUsername()).isPresent()) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пользователь с таким логином уже существует"), HttpStatus.BAD_REQUEST);
        }

        User user = new User();

        user.setUsername(registrationUserDto.getUsername());
        user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
        com.example.hotelbooking.entity.UserDetails detailsNewUser = new com.example.hotelbooking.entity.UserDetails();

        detailsNewUser.setPhone(registrationUserDto.getPhone());
        try {
            detailsNewUser.setBirthDate(LocalDate.parse(registrationUserDto.getBirthDate()));
        } catch (Exception e) {
            return new ResponseEntity<>("Неправильный формат почты", HttpStatus.BAD_REQUEST);
        }
        detailsNewUser.setEmail(registrationUserDto.getEmail());
        user.setUserDetails(detailsNewUser);


        userService.createNewUser(user);


        return new ResponseEntity<>(HttpStatus.CREATED);


    }



}
