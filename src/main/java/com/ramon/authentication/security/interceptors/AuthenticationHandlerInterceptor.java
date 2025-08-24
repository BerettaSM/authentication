package com.ramon.authentication.security.interceptors;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ramon.authentication.constants.RequestAttributes;
import com.ramon.authentication.security.annotations.SecuredEndpoint;
import com.ramon.authentication.security.exceptions.UnauthorizedException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class AuthenticationHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }
        boolean isSecuredEndpoint = handlerMethod
                .getMethodAnnotation(SecuredEndpoint.class) != null;
        if (isSecuredEndpoint &&
                request.getAttribute(RequestAttributes.AUTHENTICATED_USER) == null) {
            log.info("Denying unauthorized access to endpoint [{}].", request.getRequestURI());
            throw new UnauthorizedException();
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

}
