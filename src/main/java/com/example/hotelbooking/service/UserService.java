package com.example.hotelbooking.service;

import com.example.hotelbooking.dto.UserDetailsRequest;
import com.example.hotelbooking.dto.UserResponseDto;
import com.example.hotelbooking.entity.User;
import com.example.hotelbooking.exceptions.UserNotFoundException;
import com.example.hotelbooking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDate;
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

    @Transactional
    public void EditUserProfile(String username, UserDetailsRequest userDetailsDto)
    {
        Optional<User> byUsername = userRepository.findByUsername(username);


        if (byUsername.isPresent())
        {
            User user = byUsername.get();

            com.example.hotelbooking.entity.UserDetails userDetails=new com.example.hotelbooking.entity.UserDetails();

            userDetails.setEmail(userDetailsDto.getEmail());
            userDetails.setPhone(userDetailsDto.getPhone());
            userDetails.setBirthDate(LocalDate.parse(userDetailsDto.getBirthDate()));
            user.setUserDetails(userDetails);

        }
        else throw new UserNotFoundException("Ошибка при изменении данных пользователя: Пользователь не найден");


    }

    public UserResponseDto createUserResponseDto(User user)
    {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setUsername(user.getUsername());
        userResponseDto.setBirthDate(user.getUserDetails().getBirthDate());
        userResponseDto.setPhone(user.getUserDetails().getPhone());
        userResponseDto.setPhone(user.getUserDetails().getEmail());

        return userResponseDto;


    }


    public void deleteUser(String name) {

        Optional<User> byUserName = findByUserName(name);


        if (byUserName.isPresent())
        {
            userRepository.delete(byUserName.get());
        }
        else throw new UserNotFoundException("Ошибка удаления: пользователь не найден");


    }
}
