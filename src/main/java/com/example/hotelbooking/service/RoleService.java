package com.example.hotelbooking.service;


import com.example.hotelbooking.entity.Role;
import com.example.hotelbooking.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Сервис для ролей пользователя
 */
@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;


    /**
     * Ищет роль по её имени.
     *
     * @param name Название роли
     * @return Optional с ролью, если она найдена, или пустой Optional, если роль не найдена.
     */
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);

    }


}
