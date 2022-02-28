package com.bank.demo.auth;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@Component
public class JWTUtils {
    @Value("${jwt.secret}")
    private String jwtSecret;

    public String createToken(String accountName, Map<String,String> map) {
        Claims claims = Jwts.claims().setSubject(accountName);
        claims.put("auth", map);

        Date validityPeriod = new Date(new Date().getTime() + 1500000);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(validityPeriod)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public String getAccountName(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            String username = getAccountName(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage()+" Invalid JWT token, try logging in");
        }
    }
}
