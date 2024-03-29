package com.anthony.product.service;

import com.anthony.product.component.exception.handler.NoSuchElementFoundException;
import com.anthony.product.component.exception.handler.TokenRefreshException;
import com.anthony.product.configuration.security.JwtUtils;
import com.anthony.product.model.dto.request.TokenRefreshRequest;
import com.anthony.product.model.dto.response.TokenRefreshResponse;
import com.anthony.product.model.entity.RefreshTokenEntity;
import com.anthony.product.model.entity.UserEntity;
import com.anthony.product.repository.RefreshTokenRepository;
import com.anthony.product.repository.UserRepository;
import com.anthony.product.util.message_source.MessageSourceHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static com.anthony.product.component.exception.errors.ProductExceptionErrors.*;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    @Value("${app.jwt.refreshExpirationDateInMs}")
    private Long refreshTokenDurationMs;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    private final MessageSourceHandler messageSource;

    public Optional<RefreshTokenEntity> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public UserEntity findByUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NoSuchElementFoundException(
                messageSource.getLocalMessage(NO_ITEM_FOUND.getKey(), String.valueOf(userId)),
                messageSource.getLocalMessage(NO_ITEM_FOUND.getCode()))
        );
    }

    public RefreshTokenEntity createRefreshToken(Long userId) {
        RefreshTokenEntity refreshToken = new RefreshTokenEntity();
        refreshToken.setUsers(findByUserId(userId));
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshTokenEntity verifyExpiration(RefreshTokenEntity token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(
                    messageSource.getLocalMessage(REFRESH_TOKEN_EXPIRED_ERROR.getKey()),
                    messageSource.getLocalMessage(REFRESH_TOKEN_EXPIRED_ERROR.getCode())
            );
        }
        return token;
    }

    public TokenRefreshResponse refreshtoken(TokenRefreshRequest request, JwtUtils jwtUtils) {
        String requestRefreshToken = request.getRefreshToken();
        return findByToken(requestRefreshToken)
                .map(this::verifyExpiration)
                .map(RefreshTokenEntity::getUsers)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return new TokenRefreshResponse(token, requestRefreshToken);
                })
                .orElseThrow(
                        () -> new TokenRefreshException(
                                messageSource.getLocalMessage(REFRESH_TOKEN_ERROR.getKey()),
                                messageSource.getLocalMessage(REFRESH_TOKEN_ERROR.getCode())
                        )
                );
    }

}
