package com.ramon.authentication.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ramon.authentication.domain.dto.UserDTO;
import com.ramon.authentication.domain.entities.User;
import com.ramon.authentication.security.annotations.Authentication;
import com.ramon.authentication.security.annotations.SecuredEndpoint;


@RestController
public class MainController {
    
    @SecuredEndpoint
    @GetMapping(path = "/secured")
    public ResponseEntity<UserDTO> securedEndpoint(@Authentication User user) {
        return ResponseEntity.ok(UserDTO.from(user));
    }

    @GetMapping(path = "/unsecured")
    public ResponseEntity<UserDTO> unsecuredEndpoint() {
        return ResponseEntity.ok(UserDTO.anonymous());
    }
    
}
