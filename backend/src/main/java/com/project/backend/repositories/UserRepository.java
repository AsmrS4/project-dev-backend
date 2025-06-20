package com.project.backend.repositories;

import com.project.backend.entities.auth.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
    User findUserById(UUID id);
    User findUserByEmail(String email);
    boolean existsById(UUID id);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    @Query("SELECT u.email FROM User u")
    List<String> getUsersEmail();
}
