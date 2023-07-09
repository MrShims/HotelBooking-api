package com.example.hotelbooking.controller;


import com.example.hotelbooking.dto.UserDetailsRequest;
import com.example.hotelbooking.dto.UserResponseDto;
import com.example.hotelbooking.entity.User;
import com.example.hotelbooking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;



    @GetMapping()
    public ResponseEntity<?> getUserProfile(Principal principal) {
        Optional<User> byUserName = userService.findByUserName(principal.getName());


        if (byUserName.isPresent()) {
            UserResponseDto userResponseDto=userService.createUserResponseDto(byUserName.get());
            return new ResponseEntity<>(userResponseDto, HttpStatus.OK);

        } else return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);

    }

    @PutMapping()
    public ResponseEntity<?> editUserProfile(@RequestBody UserDetailsRequest userDetailsDto, Principal principal) {

        String username = principal.getName();

        userService.EditUserProfile(username, userDetailsDto);

        return new ResponseEntity<>(HttpStatus.OK);


    }

    @DeleteMapping()
    public ResponseEntity<?> deleteUser(Principal principal)
    {
        String name = principal.getName();

        userService.deleteUser(name);
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
