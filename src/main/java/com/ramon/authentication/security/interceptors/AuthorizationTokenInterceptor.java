package com.ramon.authentication.security.interceptors;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ramon.authentication.constants.RequestAttributes;
import com.ramon.authentication.domain.entities.User;
import com.ramon.authentication.security.exceptions.UnauthorizedException;
import com.ramon.authentication.services.TokenService;
import com.ramon.authentication.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Log4j2
public class AuthorizationTokenInterceptor implements HandlerInterceptor {

    private static final Pattern AUTHORIZATION_PATTERN = Pattern.compile("Bearer (?<token>[\\w-.]+)");

    private final TokenService tokenService;
    private final UserService userService;

    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler) throws Exception {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            log.info("Authorization header found. Processing...");
            String token = extractToken(authHeader);
            User user = getAuthenticatedUser(token);
            if (user != null) {
                log.info("Processed token from user [{}].", user.getEmail());
                request.setAttribute(RequestAttributes.AUTHENTICATED_USER, user);
            }
            else {
                log.info("Unsuccessful token processing.");
                throw new UnauthorizedException("Invalid token");
            }
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private String extractToken(String authHeader) {
        return Optional.ofNullable(authHeader)
            .map(AUTHORIZATION_PATTERN::matcher)
            .filter(Matcher::matches)
            .map(m -> m.group("token"))
            .orElse(null);
    }

    private User getAuthenticatedUser(String token) {
        return Optional.ofNullable(token)
            .map(tokenService::verify)
            .map(DecodedJWT::getSubject)
            .map(userService::findByEmail)
            .map(Optional::get)
            .orElse(null);
    }

}
