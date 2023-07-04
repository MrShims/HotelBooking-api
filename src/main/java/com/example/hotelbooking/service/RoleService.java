package com.example.hotelbooking.service;


import com.example.hotelbooking.entity.Role;
import com.example.hotelbooking.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;


    public Optional<Role> findByName(String name){
       return roleRepository.findByName(name);

    }







}
