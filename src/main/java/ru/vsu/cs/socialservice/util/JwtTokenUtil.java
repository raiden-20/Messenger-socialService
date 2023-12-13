package ru.vsu.cs.socialservice.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class JwtTokenUtil {
    @Value("${jwt.secret}")
    private String signature;

    public UUID retrieveIdClaim(String token) throws JWTVerificationException {
        token = token.substring(7);
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(signature))
                .withSubject("User details")
                .withIssuer("auth-service")
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return UUID.fromString(jwt.getClaim("id").asString());
    }
}
