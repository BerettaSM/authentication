package com.ramon.authentication.domain.dto;

import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class TokenDTO {

    private String tokenType;
    private String accessToken;
    private Long expiresIn;

    public TokenDTO(DecodedJWT jwt) {
        tokenType = "Bearer";
        accessToken = jwt.getToken();
        expiresIn = jwt.getExpiresAtAsInstant().getEpochSecond();
    }

    public static TokenDTO from(DecodedJWT jwt) {
        return new TokenDTO(jwt);
    }

}
