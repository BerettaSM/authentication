package com.ramon.authentication.domain.dto;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class TokenDTO {

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
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
