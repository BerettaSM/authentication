package com.ramon.authentication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ramon.authentication.domain.entities.User;

public interface UserRepository extends JpaRepository<User, String> {  
}
