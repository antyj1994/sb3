package com.aarci.sb3.config.security.utility;

import com.aarci.sb3.entity.Permesso;
import com.aarci.sb3.entity.Utente;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;

import java.util.Date;

public class JWTUtil {
    private static final long EXPIRE_DURATION = 3600000L;
    private static final String SECRET_KEY = "ThisIsSecretKey";

    public static String generateAccessToken(Utente utente) {

        StringBuilder sb = new StringBuilder();
        String claims;
        sb.append("[");
        for (Permesso p : utente.getPermessi()){
            sb.append(p.getNome()).append(", ");
        }
        sb.delete(sb.lastIndexOf(","), sb.lastIndexOf(",")+2);
        sb.append("]");
        claims = sb.toString();

        return Jwts.builder()
                .setSubject(String.format("%s", utente.getEmail()))
                .setIssuer("CodeJava")
                .claim("roles", claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public static Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

}
