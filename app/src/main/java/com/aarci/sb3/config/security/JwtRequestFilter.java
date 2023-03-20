package com.aarci.sb3.config.security;

import com.aarci.sb3.entity.Permesso;
import com.aarci.sb3.config.security.utility.JWTUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil;

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

        Claims claims = this.jwtUtil.getAllClaimsFromToken(token);

        if (claims == null){
            return securityUser;
        }

        String roles = (String) claims.get("roles");

        if (roles == null) {
            return securityUser;
        }

        roles = roles.replace("[", "").replace("]", "");
        String[] roleNames = roles.split(", ");

        for (String roleName : roleNames) {
            securityUser.getPermessi().add(new Permesso(roleName));
        }

        return securityUser;
    }


}
