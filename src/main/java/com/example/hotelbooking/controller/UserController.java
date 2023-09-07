package com.example.hotelbooking.controller;


import com.example.hotelbooking.dto.UserDetailsRequest;
import com.example.hotelbooking.dto.UserResponseDto;
import com.example.hotelbooking.entity.User;
import com.example.hotelbooking.exceptions.UserNotFoundException;
import com.example.hotelbooking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping()
    public ResponseEntity<?> getUserProfile(Principal principal) {


        Optional<User> byUserName = userService.findByUserName(principal.getName());
        if (byUserName.isPresent()) {
            UserResponseDto userResponseDto = userService.createUserResponseDto(byUserName.get());
            return new ResponseEntity<>(userResponseDto, HttpStatus.OK);

        } else throw new UserNotFoundException("Пользователь не найден");

    }

    @PutMapping()
    public ResponseEntity<?> editUserProfile(@Valid @RequestBody UserDetailsRequest userDetailsDto, Principal principal, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<String> error = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        String username = principal.getName();

        userService.EditUserProfile(username, userDetailsDto);

        return new ResponseEntity<>(HttpStatus.OK);


    }

    @DeleteMapping()
    public ResponseEntity<?> deleteUser(Principal principal) {
        String name = principal.getName();

        userService.deleteUser(name);
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
