package com.ramon.authentication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ramon.authentication.domain.dto.TokenDTO;
import com.ramon.authentication.domain.dto.UserDTO;
import com.ramon.authentication.services.AuthService;
import com.ramon.authentication.validation.groups.Signup;

import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "/signup")
    public ResponseEntity<UserDTO> signup(
            @Validated({ Default.class, Signup.class }) @RequestBody UserDTO dto) {
        UserDTO user = authService.signup(dto);
        return ResponseEntity.ok(user);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<TokenDTO> login(@Valid @RequestBody UserDTO dto) {
        TokenDTO token = authService.login(dto);
        return ResponseEntity.ok(token);
    }

}
