package com.project.backend.services.auth;

import com.project.backend.dto.auth.LoginDto;
import com.project.backend.dto.auth.RegisterDto;
import com.project.backend.dto.jwt.AccessToken;
import com.project.backend.entities.auth.User;
import com.project.backend.repositories.UserRepository;
import com.project.backend.utils.jwt.AccessTokenUtil;
import com.project.backend.utils.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final AccessTokenUtil tokenUtil;
    private final UserMapper mapper;
    @Override
    public Object login(LoginDto loginDto) {
        User user = userRepository.findUserByEmail(loginDto.getEmail());
        if(user == null || !Objects.equals(user.getPassword(), loginDto.getPassword())) {
            throw new RuntimeException("Неверный логин или пароль");
        }
        AccessToken token = tokenUtil.getAccessToken();
        return token;
    }

    @Override
    public Object register(RegisterDto registerDto) {
        if(userRepository.existsByEmail(registerDto.getEmail())) {
            throw new RuntimeException("Email " + registerDto.getEmail() + " занят");
        }
        User user = mapper.map(registerDto);
        userRepository.save(user);
        return null;
    }

    @Override
    public Object logout() {
        return null;
    }
}
