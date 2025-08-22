package com.ramon.authentication.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ramon.authentication.domain.entities.User;

@Service
public class TokenService {

    @Value("${security.client-id}")
    private String clientId;

    @Value("${security.client-secret}")
    private String clientSecret;

    @Value("${security.jwt.duration-in-seconds}")
    private Integer tokenDurationInSeconds;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(clientSecret);
            return JWT.create()
                    .withIssuer(clientId)
                    .withSubject(user.getEmail())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Failed to generate token", e);
        }
    }

    public DecodedJWT verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(clientSecret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(clientId)
                    .build();
            return verifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Failed to verify token", e);
        }
    }

    private Instant generateExpirationDate() {
        return Instant.now().plus(tokenDurationInSeconds, ChronoUnit.SECONDS);
    }

}
