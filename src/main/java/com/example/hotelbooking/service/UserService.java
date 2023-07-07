package com.example.hotelbooking.service;

import com.example.hotelbooking.entity.User;
import com.example.hotelbooking.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {


    private final UserRepository userRepository;
    private final RoleService roleService;


    public Optional<User> findByUserName(String username) {
        return userRepository.findByUsername(username);
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = findByUserName(username).orElseThrow(() -> new UsernameNotFoundException(
                "User not found"
        ));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );

    }

    public User createNewUser(User user) {

        user.setRoles(List.of(roleService.findByName("ROLE_USER").get()));

        return userRepository.save(user);


    }
}
