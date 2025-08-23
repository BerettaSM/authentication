package com.ramon.authentication.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
@ConfigurationProperties(prefix = "security.jwt")
public class TokenProps {

    private String secret;
    private Integer durationInSeconds;

}
