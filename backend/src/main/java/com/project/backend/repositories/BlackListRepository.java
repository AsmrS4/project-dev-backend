package com.project.backend.repositories;

import com.project.backend.entities.auth.Token;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BlackListRepository extends CrudRepository<Token, Long> {
    boolean existsByToken(String token);

    Token getTokenByToken(String token);

    @Transactional
    @Modifying
    @Query("SELECT t FROM Token t WHERE t.userId = :userId")
    List<Token> getUsersTokens(@Param("userId") UUID userId);


}
