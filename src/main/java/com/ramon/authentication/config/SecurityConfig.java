package com.ramon.authentication.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ramon.authentication.security.interceptors.AuthenticationHandlerInterceptor;
import com.ramon.authentication.security.interceptors.AuthorizationTokenInterceptor;
import com.ramon.authentication.security.resolvers.AuthenticationResolver;

@Configuration
public class SecurityConfig implements WebMvcConfigurer {

    @Autowired
    private AuthenticationResolver authenticationResolver;

    @Autowired
    private AuthorizationTokenInterceptor authorizationTokenInterceptor;

    @Autowired
    private AuthenticationHandlerInterceptor authenticationHandlerInterceptor;

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(authorizationTokenInterceptor);
        registry.addInterceptor(authenticationHandlerInterceptor);
    }

    @Override
    public void addArgumentResolvers(@NonNull List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticationResolver);
    }

    @Bean
    @SuppressWarnings("unused")
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
