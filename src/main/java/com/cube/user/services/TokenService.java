package com.cube.user.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.cube.user.exceptions.InternalException;
import com.cube.user.exceptions.UnauthorizedException;
import com.cube.user.dtos.internal.ExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Slf4j
@Service
public class TokenService {

    @Value("${spring.application.name}")
    private String issuer;

    @Value("${security.token.secret}")
    private String secret;

    @Value("${security.token.minutesToExpire}")
    private String minutesToExpire;


    public String generateToken (UserDetails user) {
        try {
            log.info("Start generating auth token");
            Algorithm algorithm = Algorithm.HMAC512(secret);

            return JWT.create()
                    .withIssuer(issuer)
                    .withSubject(user.getUsername())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);

        } catch (JWTCreationException ex) {
            log.warn("Error generating token with message [{}]", ex.getMessage());

            throw new InternalException(ExceptionCode.FAILED_TO_GENERATE_TOKEN);
        }
    }

    public String validateToken (String token) throws UnauthorizedException {
        try {
            Algorithm algorithm = Algorithm.HMAC512(secret);

            return JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException ex) {
            log.warn("Invalid token sent. Got the following exception message: [{}]", ex.getMessage());

            throw new UnauthorizedException(ExceptionCode.INVALID_TOKEN);
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now()
                .plusMinutes(Integer.parseInt(minutesToExpire))
                .toInstant(ZoneOffset.of("-03:00"));
    }
}
