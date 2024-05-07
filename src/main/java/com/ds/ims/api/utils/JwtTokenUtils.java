package com.ds.ims.api.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Утилиты для работы с JWT-токеном
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class JwtTokenUtils {
    /**
     * Секретный ключ для подписи JWT-токена
     */
    @Value("${jwt.secret}")
    String secret;

    /**
     * Время жизни JWT-токена
     */
    @Value("${jwt.lifetime}")
    Duration jwtLifetime;

    /**
     * Генерация JWT-токена
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> rolesList = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("roles", rolesList);
        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + jwtLifetime.toMillis());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    /**
     * Получение имени пользователя из JWT-токена
     */
    public String getUsername(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    /**
     * Получение ролей пользователя из JWT-токена
     */
    public List<String> getRoles(String token) {
        return (List<String>) getAllClaimsFromToken(token).get("roles");
    }

    /**
     * Получение всех данных из JWT-токена
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}
