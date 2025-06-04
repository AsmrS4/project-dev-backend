package com.project.backend.services.auth;

import com.project.backend.dto.auth.LoginDto;
import com.project.backend.dto.auth.RegisterDto;
import com.project.backend.entities.auth.User;
import com.project.backend.enums.Role;
import com.project.backend.exceptions.LoginFailedException;
import com.project.backend.exceptions.NotUniqueException;
import com.project.backend.repositories.UserRepository;
import com.project.backend.utils.jwt.TokenService;
import com.project.backend.utils.mapper.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final UserMapper mapper;

    private UUID generateUUID() {return UUID.randomUUID();}

    @Override
    public Object login(LoginDto loginDto) {
        User user = userRepository.findUserByEmail(loginDto.getEmail());
        if(user == null || !Objects.equals(user.getPassword(), loginDto.getPassword())) {
            throw new LoginFailedException("Неверный логин или пароль");
        }
        UserDetails userDetails = loadUserByUsername(user.getEmail());

        return tokenService.getAccessToken(userDetails);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Object register(RegisterDto registerDto) {
        if(userRepository.existsByEmail(registerDto.getEmail())) {
            throw new NotUniqueException("Email " + registerDto.getEmail() + " занят");
        }
        User user = mapper.map(registerDto);
        user.setId(generateUUID());

        userRepository.save(user);

        LoginDto loginDto = new LoginDto();
        loginDto.setEmail(user.getEmail());
        loginDto.setPassword(user.getPassword());

        return login(loginDto);
    }

    @Override
    public Object logout() {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if(user == null ){
            throw new UsernameNotFoundException(String.format("Пользователь с логином %s не найден", email));
        }
        List<Role> roles = new ArrayList<>();
        roles.add(user.getRole());
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                roles.stream()
                        .map(role-> new SimpleGrantedAuthority(
                                role.toString()))
                        .collect(Collectors.toList())
        );
    }


}
