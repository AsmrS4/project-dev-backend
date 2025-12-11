package com.project.backend.utils.jwt;

import com.project.backend.dto.jwt.AccessToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.micrometer.common.lang.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TokenService {
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.lifetime}")
    private Duration lifeTime;
    public AccessToken getAccessToken(UserDetails details) {
        String accessToken = this.generateAccessToken(details);
        return new AccessToken(accessToken);
    }

    public String getUserId(String token) {
        return getClaims(token, secretKey).getSubject();
    }

    public List<?> getUserRoles(String token) {
        return getClaims(token, secretKey).get("role", List.class);
    }


    private String generateAccessToken(UserDetails details) {
        final Date issuedDate = new Date();
        final Date expiredDate = new Date(issuedDate.getTime() + lifeTime.toMillis());
        List<String> roles = details.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(details.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .claim("role", roles)
                .compact();
    }
    private Claims getClaims(@NonNull String token, @NonNull String secret) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

}
