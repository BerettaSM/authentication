package com.ramon.authentication.security.resolvers;

import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolverComposite;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.ramon.authentication.constants.RequestAttributes;
import com.ramon.authentication.domain.entities.User;
import com.ramon.authentication.security.annotations.Authentication;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class AuthenticationResolver extends HandlerMethodArgumentResolverComposite {

    @Override
    public boolean supportsParameter(@NonNull MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Authentication.class) &&
                parameter.getParameterType().equals(User.class);
    }

    @Override
    public Object resolveArgument(
            @NonNull MethodParameter parameter,
            @Nullable ModelAndViewContainer mavContainer,
            @NonNull NativeWebRequest webRequest,
            @Nullable WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        return request == null ? null : request.getAttribute(RequestAttributes.AUTHENTICATED_USER);
    }

}
