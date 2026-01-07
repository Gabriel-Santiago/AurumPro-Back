package com.AurumPro.security;

import com.AurumPro.entities.empresa.Empresa;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secretKey;

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public boolean validateToken(String token, UserDetails user) {
        String username = extractUsername(token);
        return username.equals(user.getUsername()) && !isTokenExpired(token);
    }

    public String generateToken(Empresa empresa) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.create()
                    .withIssuer("AurumPro")
                    .withSubject(empresa.getEmail())
                    .withClaim("empresaId", empresa.getId())
                    .withExpiresAt(generateExpiratedDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new IllegalArgumentException("Invalid JWT Token", exception);
        }
    }

    private DecodedJWT getClaims(String token) {
        return JWT
                .require(Algorithm.HMAC256(secretKey))
                .build()
                .verify(token);
    }

    private boolean isTokenExpired(String token) {
        return getClaims(token)
                .getExpiresAt()
                .before(new Date());
    }

    private Instant generateExpiratedDate() {
        return LocalDateTime
                .now()
                .plusMinutes(15)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}
