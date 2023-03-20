package com.aarci.sb3.config.security.utility;

import com.aarci.sb3.entity.Permesso;
import com.aarci.sb3.entity.Utente;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    @Autowired
    private JWTConfigurationProperties jwtConfigurationProperties;

    private static final String BEARER = "Bearer ";

    public String generateAccessToken(Utente utente) {

        StringBuilder sb = new StringBuilder();
        String roles = null;

        if (!utente.getPermessi().isEmpty()) {
            sb.append("[");
            for (Permesso p : utente.getPermessi()) {
                sb.append(p.getNome()).append(", ");
            }
            sb.delete(sb.lastIndexOf(","), sb.lastIndexOf(",") + 2);
            sb.append("]");
            roles = sb.toString();
        }

        return Jwts.builder()
                .setSubject(String.format("%s", utente.getEmail()))
                .setIssuer("SB3")
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfigurationProperties.getExpireDuration()))
                .signWith(SignatureAlgorithm.HS512, jwtConfigurationProperties.getSecretKey())
                .compact();
    }

    public Claims getAllClaimsFromToken(String token) {

        if (token.startsWith(BEARER)){
            token = token.replaceFirst(BEARER, "");
        }

        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(jwtConfigurationProperties.getSecretKey())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

}
