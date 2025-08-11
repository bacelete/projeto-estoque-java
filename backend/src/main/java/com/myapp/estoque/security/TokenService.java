package com.myapp.estoque.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.myapp.estoque.model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(Usuario usuario) {
        try {
            System.out.println("Secret: "+secret); //remover dps
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
            return token;
        }

        catch(JWTCreationException exception) {
            throw new RuntimeException("Erro na hora de gerar o token!", exception);
        }
    }

    public String validateToken(String token) {
        Objects.requireNonNull(secret, "A chave secreta não pode ser nula.");

        try {
            System.out.println("Secret: "+secret); //remover dps
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT decoded = JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token);

            return decoded.getSubject();
        }
        catch(JWTVerificationException exception) {
            throw new RuntimeException("Token inválido: "+ exception.getMessage());
        }
    }

    private Instant generateExpirationDate() {
        return Instant.now().plus(Duration.ofHours(5));
    }
}
