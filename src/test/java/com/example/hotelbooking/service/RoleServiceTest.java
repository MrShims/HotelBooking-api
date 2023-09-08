package com.example.hotelbooking.service;

import com.example.hotelbooking.entity.Role;
import com.example.hotelbooking.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RoleServiceTest {

    @InjectMocks
    private RoleService roleService;

    @Mock
    private RoleRepository roleRepository;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);


    }

    @Test
    public void testFindByName_ExistingRole() {
        String roleName = "ROLE_USER";
        Role role = new Role();
        role.setName(roleName);

        when(roleRepository.findByName(roleName)).thenReturn(Optional.of(role));

        Optional<Role> result = roleService.findByName(roleName);

        assertTrue(result.isPresent());
        assertEquals(roleName, result.get().getName());

        verify(roleRepository).findByName(roleName);
    }

    @Test
    public void testFindByName_NonExistingRole() {
        String roleName = "ROLE_ADMIN";

        when(roleRepository.findByName(roleName)).thenReturn(Optional.empty());

        Optional<Role> result = roleService.findByName(roleName);

        assertFalse(result.isPresent());

        verify(roleRepository).findByName(roleName);
    }
}