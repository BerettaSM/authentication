package com.ramon.authentication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ramon.authentication.domain.dto.UserDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Transactional
    public UserDTO signup(UserDTO dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        return userService.save(dto);
    }

}
