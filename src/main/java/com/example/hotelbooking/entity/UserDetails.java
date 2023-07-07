package com.example.hotelbooking.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "users_details")
@Data
public class UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "email")
    private String email;

    @Column(name = "birthdate")
    private LocalDate BirthDate;

    @Column(name = "phone_number")
    private String phone;








}
