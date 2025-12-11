package com.project.backend.configuration;

import com.project.backend.exceptions.UnauthorizedException;
import com.project.backend.repositories.BlackListRepository;
import com.project.backend.utils.jwt.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.stream.Collectors;

@Component

public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService accessTokenService;
    @Autowired
    private BlackListRepository blackListRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String userId = null;
        String jwt = null;

        if(authHeader!=null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            if (blackListRepository.existsByToken(jwt)) {
                throw new UnauthorizedException("Token is blacklisted");
            }
            try {
                userId = accessTokenService.getUserId(jwt);
            }catch (ExpiredJwtException e) {
                logger.debug("Token is expired");
                throw new UnauthorizedException("Token is expired");
            }
        }


        if(userId!=null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    userId, jwt, accessTokenService
                    .getUserRoles(jwt).stream()
                    .map(role-> new SimpleGrantedAuthority(
                            role.toString()))
                    .collect(Collectors.toList())
            );
            SecurityContextHolder.getContext().setAuthentication(token);
        }
        filterChain.doFilter(request, response);

    }
}
