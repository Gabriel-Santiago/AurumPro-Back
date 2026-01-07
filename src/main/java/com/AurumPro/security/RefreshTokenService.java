package com.AurumPro.security;

import com.AurumPro.entities.empresa.Empresa;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private static final long REFRESH_TOKEN_DURATION_DAYS = 3;

    private final RefreshTokenRepository repository;

    public RefreshTokenService(RefreshTokenRepository repository) {
        this.repository = repository;
    }

    public RefreshToken createRefreshToken(Empresa empresa) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setEmpresa(empresa);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(
                Instant.now().plus(REFRESH_TOKEN_DURATION_DAYS, ChronoUnit.DAYS)
        );

        return repository.save(refreshToken);
    }

    public RefreshToken validateRefreshToken(String token) {
        RefreshToken refreshToken = repository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Refresh token inválido"));

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            repository.delete(refreshToken);
            throw new RuntimeException("Refresh token expirado");
        }

        return refreshToken;
    }

    @Transactional
    public void deleteByEmpresa(Long empresa) {
        repository.deleteByEmpresaId(empresa);
    }

    @Transactional
    public RefreshToken rotateRefreshToken(RefreshToken oldToken) {
        repository.delete(oldToken);
        return createRefreshToken(oldToken.getEmpresa());
    }
}
