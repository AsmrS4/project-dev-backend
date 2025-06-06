package com.project.backend.configuration;

import com.project.backend.entities.auth.Token;
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
import java.util.List;
import java.util.Objects;
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
        String userEmail = null;
        String jwt = null;

        if(authHeader!=null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            try {
                userEmail = accessTokenService.getUserLogin(jwt);
            }catch (ExpiredJwtException e) {
                logger.debug("Token is expired");
            }
        }
        List<Token> blacklist = blackListRepository.getUsersTokens(userEmail);
        blacklist.stream().map(token -> {
            if(Objects.equals(token.getToken(), authHeader.substring(7))) {
                throw new UnauthorizedException("Unauthorized");
            }
            return null;
        });

        if(userEmail!=null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    userEmail, jwt, accessTokenService
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
