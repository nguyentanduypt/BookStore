package com.example.bookstore.services;

import com.example.bookstore.models.auth.AuthRequest;
import com.example.bookstore.models.auth.AuthResponse;
import com.example.bookstore.models.auth.RefreshTokenRequest;

public interface AuthService {
    AuthResponse authenticate(AuthRequest authRequest);
    AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
