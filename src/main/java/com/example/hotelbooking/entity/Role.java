package com.example.hotelbooking.entity;


import lombok.Data;

import javax.persistence.*;


@Entity
@Data
@Table(name = "roles")
public class Role {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name = "name")
    private String name;


}
