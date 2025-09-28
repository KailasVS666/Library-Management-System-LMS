package com.lms.service;

import com.lms.dto.UserDto;
import com.lms.entity.Role;
import com.lms.entity.User;
import com.lms.repository.RoleRepository;
import com.lms.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUser(UserDto userDto) {
        Role role = roleRepository.findByName("ROLE_USER");
        if (role == null) {
            role = new Role("ROLE_USER");
            roleRepository.save(role);
        }

        User user = new User(
            userDto.getUsername(),
            passwordEncoder.encode(userDto.getPassword()),
            Arrays.asList(role)
        );
        userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}