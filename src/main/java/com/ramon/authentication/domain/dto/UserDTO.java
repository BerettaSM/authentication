package com.ramon.authentication.domain.dto;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ramon.authentication.domain.entities.User;
import com.ramon.authentication.validation.groups.Signup;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    
    @NotBlank(groups = Signup.class, message = "Name must not be blank")
    @Length(groups = Signup.class, min = 3, message = "Name must have at least 3 characters")
    private String name;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "A valid email must be provided")
    private String email;

    @NotBlank(message = "Password must not be blank")
    @Pattern(groups = Signup.class, regexp = ".*\\d.*", message = "Must contain a number")
    @Pattern(groups = Signup.class, regexp = ".*\\p{Lower}.*", message = "Must contain a lowercase letter")
    @Pattern(groups = Signup.class, regexp = ".*\\p{Upper}.*", message = "Must contain an uppercase letter")
    @Pattern(groups = Signup.class, regexp = ".*\\p{Punct}.*", message = "Must contain a symbol")
    @Length(groups = Signup.class, min = 8, message = "Must have at least 8 characters")
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
