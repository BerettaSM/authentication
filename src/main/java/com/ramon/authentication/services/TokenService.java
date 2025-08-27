package com.ramon.authentication.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ramon.authentication.config.SecurityProps;
import com.ramon.authentication.config.TokenProps;
import com.ramon.authentication.domain.entities.User;

@Service
public class TokenService {

    private final SecurityProps securityProps;
    private final TokenProps tokenProps;
    private final Algorithm algorithm;
    private final JWTVerifier verifier;

    public TokenService(SecurityProps securityProps, TokenProps tokenProps) {
        this.securityProps = securityProps;
        this.tokenProps = tokenProps;
        algorithm = Algorithm.HMAC256(tokenProps.getSecret());
        verifier = JWT.require(algorithm)
                .withIssuer(securityProps.getClientId())
                .build();
    }

    public DecodedJWT generateToken(User user) {
        String token = JWT.create()
                .withAudience(securityProps.getClientId())
                .withIssuer(securityProps.getClientId())
                .withIssuedAt(Instant.now())
                .withExpiresAt(generateExpirationDate())
                .withSubject(user.getEmail())
                .sign(algorithm);
        return JWT.decode(token);
    }

    public DecodedJWT verify(String token) {
        try {
            return verifier.verify(token);
        }
        catch (JWTVerificationException e) {
            return null;
        }
    }

    private Instant generateExpirationDate() {
        return Instant.now().plus(tokenProps.getDurationInSeconds(), ChronoUnit.SECONDS);
    }

}
