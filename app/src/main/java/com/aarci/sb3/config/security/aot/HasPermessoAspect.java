package com.aarci.sb3.config.security.aot;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Log4j2
@Aspect
@Component
public class HasPermessoAspect {

    private static final String ERROR_MESSAGE = "Access Denied";

    @Value("${sb3.method-security.enabled}")
    private boolean isMethodSecurityEnabled;

    @Autowired
    private HttpServletResponse response;

    @Around("@annotation(HasPermesso)")
    public Object authorizeRequest(ProceedingJoinPoint joinPoint) throws Throwable {

        SimpleGrantedAuthority requestedPermissionName = retrieveRequestedPermissionValue(joinPoint);

        Authentication credentials = SecurityContextHolder.getContext().getAuthentication();

        if (isMethodSecurityEnabled && !credentials.getAuthorities().contains(requestedPermissionName)){
            log.info("Denying request for missing permission: {}", requestedPermissionName.getAuthority());
            return rejectRequestWithForbiddenStatus();
        }

        return joinPoint.proceed();

    }

    private Object rejectRequestWithForbiddenStatus() {
        response.setStatus(403);
        try {
            response.getOutputStream().write(ERROR_MESSAGE.getBytes());
        } catch(IOException e){
            log.error("Error occurred while obtaining output stream", e);
        }
        return null;
    }

    private SimpleGrantedAuthority retrieveRequestedPermissionValue(ProceedingJoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String permessoRequested = signature.getMethod().getAnnotation(HasPermesso.class).value();
        return new SimpleGrantedAuthority(permessoRequested);
    }

}