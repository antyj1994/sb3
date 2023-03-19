package com.aarci.sb3.config.security;

import com.aarci.sb3.entity.Permesso;
import com.aarci.sb3.config.security.utility.JWTUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        final String authToken = request.getHeader("Authorization");

        if (authToken != null) {
            setAuthenticationContext(authToken, request);
        }
        chain.doFilter(request, response);
    }

    private void setAuthenticationContext(String token, HttpServletRequest request) {

        SecurityUser securityUser = getUserDetails(token);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private SecurityUser getUserDetails(String token) {

        SecurityUser securityUser = new SecurityUser();

        Claims claims = JWTUtil.getAllClaimsFromToken(token);

        if (claims == null){
            return securityUser;
        }

        String subject = (String) claims.get(Claims.SUBJECT);
        String roles = (String) claims.get("roles");

        roles = roles.replace("[", "").replace("]", "");
        String[] roleNames = roles.split(", ");

        for (String roleName : roleNames) {
            securityUser.getPermessi().add(new Permesso(roleName));
        }

        String[] jwtSubject = subject.split(",");

        securityUser.setUsername(jwtSubject[0]);

        return securityUser;
    }


}
