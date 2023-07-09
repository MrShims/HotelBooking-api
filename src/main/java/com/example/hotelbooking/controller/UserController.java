package com.example.hotelbooking.controller;

import com.example.hotelbooking.dto.*;
import com.example.hotelbooking.entity.User;
import com.example.hotelbooking.exceptions.AppError;
import com.example.hotelbooking.service.UserService;
import com.example.hotelbooking.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final JwtTokenUtils jwtTokenUtils;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Неправильный логин или пароль"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        return new ResponseEntity(new JwtResponse(token), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
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
        detailsNewUser.setBirthDate(LocalDate.parse(registrationUserDto.getBirthDate()));
        detailsNewUser.setEmail(registrationUserDto.getEmail());
        user.setUserDetails(detailsNewUser);


        userService.createNewUser(user);


        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping("/info")
    public ResponseEntity<?> getUserProfile(Principal principal) {
        Optional<User> byUserName = userService.findByUserName(principal.getName());


        if (byUserName.isPresent()) {
            UserResponseDto userResponseDto=userService.createUserResponseDto(byUserName.get());
            return new ResponseEntity<>(userResponseDto, HttpStatus.OK);

        } else return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);

    }

    @PutMapping("/info")
    public ResponseEntity<?> editUserProfile(@RequestBody UserDetailsRequest userDetailsDto, Principal principal) {


        String username = principal.getName();


        userService.EditUserProfile(username, userDetailsDto);

        return new ResponseEntity<>(HttpStatus.OK);


    }


}
