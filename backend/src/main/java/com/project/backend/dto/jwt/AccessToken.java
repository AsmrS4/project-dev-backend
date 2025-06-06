package com.project.backend.dto.jwt;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AccessToken {
    private final String accessToken;
}
