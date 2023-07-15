package com.example.hotelbooking.controller;

import com.example.hotelbooking.dto.UserResponseDto;
import com.example.hotelbooking.entity.User;
import com.example.hotelbooking.service.UserService;
import org.aspectj.lang.annotation.Before;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@WebMvcTest(UserController.class)

class UserControllerTest {


    @Test
    void TestGetUserProfile() throws Exception {

    }

    @Test
    void editUserProfile() {
    }

    @Test
    void deleteUser() {
    }
}