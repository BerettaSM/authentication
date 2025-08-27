package com.ramon.authentication.services;

import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ramon.authentication.domain.dto.TokenDTO;
import com.ramon.authentication.domain.dto.UserDTO;
import com.ramon.authentication.domain.entities.User;
import com.ramon.authentication.security.exceptions.UnauthorizedException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final TokenService tokenService;

    @Transactional
    public UserDTO signup(UserDTO dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        return userService.save(dto);
    }

    @Transactional
    public TokenDTO login(UserDTO dto) {
        User user = userService.findByEmail(dto.getEmail())
                .filter(matchPassword(dto.getPassword()))
                .orElseThrow(() -> new UnauthorizedException("Invalid email/password"));
        DecodedJWT token = tokenService.generateToken(user);
        return TokenDTO.from(token);
    }

    private Predicate<? super User> matchPassword(String password) {
        return user -> passwordEncoder.matches(password, user.getPassword());
    }

}
