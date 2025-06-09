package com.project.backend.services.user;


import com.project.backend.dto.user.UserDto;
import com.project.backend.dto.user.UserUpdateDto;
import com.project.backend.entities.auth.User;
import com.project.backend.exceptions.NotUniqueException;
import com.project.backend.exceptions.UnauthorizedException;
import com.project.backend.repositories.UserRepository;
import com.project.backend.utils.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;
    @Override
    public Object update(UserUpdateDto updateDto) {
        User user = userRepository.findUserById(UUID.fromString(getAuthId()));
        if(user == null) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        User otherUser = userRepository.findUserByEmail(updateDto.getEmail());
        if(otherUser != null && !user.equals(otherUser)) {
            throw new NotUniqueException("Этот email уже занят");
        }
        if(updateDto.getPhoneNumber()!=null
                && userRepository.existsByPhoneNumber(updateDto.getPhoneNumber())
                && !Objects.equals(user.getPhoneNumber(), updateDto.getPhoneNumber())
        ) {
            throw new NotUniqueException("Этот номер телефона уже занят");
        }
        userRepository.save(mapper.map(user, updateDto));
        return true;
    }

    @Override
    public UserDto getUserProfile() {
        User user = userRepository.findUserById(UUID.fromString(getAuthId()));
        if(user == null) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return mapper.mapUser(user);
    }

    private String getAuthId() {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        if(id == null) {
            throw new UnauthorizedException("Authorization is required");
        }
        return id;
    }
}
