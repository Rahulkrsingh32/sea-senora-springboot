package com.backend.seasenora.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.backend.seasenora.exceptions.JWTTokenMalformedException;
import com.backend.seasenora.exceptions.JwtTokenMissingException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Component
public class JwtUtil {

    final private static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
            .getLogger(JwtUtil.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.tokenValidity}")
    private long tokenValidity;

    public String getUserName(String token) {
        try {
            Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJwt(token).getBody();
            return body.getSubject();
        } catch (Exception e) {
            log.error("Cannot Get UserName from token");
            e.printStackTrace();
        }
        return null;
    }

    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        long currentTimeInMills = System.currentTimeMillis();
        long expirationInMills = currentTimeInMills + tokenValidity;
        Date expDate = new Date(expirationInMills);
        return Jwts.builder().setClaims(claims)
                .setExpiration(expDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

    }

    public void validateToken(final String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        } catch (SignatureException e) {
            log.error("Invalid JWT Signature");
            throw new JWTTokenMalformedException("Invalid JWT Signature");
        } catch (ExpiredJwtException ex) {
            log.error("Invaid Expired Signature");
            throw new JWTTokenMalformedException("Expired Jwt Token");
        } catch (IllegalArgumentException ie) {
            log.error("Jwt Claims is missing Signature");
            throw new JwtTokenMissingException("Jwt Claims is missing");
        }

    }
}
