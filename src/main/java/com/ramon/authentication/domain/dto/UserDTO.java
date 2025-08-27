package com.ramon.authentication.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ramon.authentication.domain.entities.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    
    private String name;
    private String email;
    private String password;

    public UserDTO(User user) {
        name = user.getName();
        email = user.getEmail();
        password = null;
    }

    public User toEntity() {
        return new User(email, name, password);
    }

    public static UserDTO from(User user) {
        return new UserDTO(user);
    }

    public static UserDTO anonymous() {
        return new UserDTO("Anonymous", null, null);
    }

}
