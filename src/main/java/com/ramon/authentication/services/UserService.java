package com.ramon.authentication.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ramon.authentication.domain.dto.UserDTO;
import com.ramon.authentication.domain.entities.User;
import com.ramon.authentication.repositories.UserRepository;
import com.ramon.authentication.services.exceptions.DatabaseException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findById(email);
    }

    @Transactional
    public UserDTO save(UserDTO dto) {
        if (userRepository.existsById(dto.getEmail())) {
            throw new DatabaseException(
                    "User already registered",
                    HttpStatus.CONFLICT);
        }
        User user = dto.toEntity();
        User saved = userRepository.save(user);
        return UserDTO.from(saved);
    }

}
