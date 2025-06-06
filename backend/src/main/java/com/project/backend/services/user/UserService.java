package com.project.backend.services.user;

import com.project.backend.dto.user.UserDto;
import com.project.backend.dto.user.UserUpdateDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    Object update(UserUpdateDto updateDto);
    UserDto getUserProfile();
}
