package com.ramon.authentication.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PACKAGE, force = true)
@Table(name = "TBL_USER")
public class User {
    
    @Id
    @Column(unique = true, nullable = false)
    @EqualsAndHashCode.Include
    private String email;
    private String name;
    private String password;
    
}
