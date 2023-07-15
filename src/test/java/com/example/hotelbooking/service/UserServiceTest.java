package com.example.hotelbooking.service;

import com.example.hotelbooking.entity.Role;
import com.example.hotelbooking.entity.User;
import com.example.hotelbooking.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private UserService userService;



    @Test
    void createNewUser() {

    }
}