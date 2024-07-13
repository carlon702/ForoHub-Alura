package com.cjm.forum_hub.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cjm.forum_hub.domain.users.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("api.security.secret")
    private String apiSecret;

    public String genToken(User user){
        try{
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);

            return JWT.create()
                    .withIssuer("forumHub")
                    .withSubject(user.getUsername())
                    .withClaim("id", user.getId())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
        }
        catch(JWTCreationException exception){
            throw new RuntimeException(exception.getMessage());
        }
    }

    public String getSubject(String token){
        try{
            if(token==null){
                throw new RuntimeException();
            }

            DecodedJWT decodedJWT;
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("forumHub")
                    .build();
            decodedJWT = verifier.verify(token);

            if(decodedJWT.getSubject() != null){
                return decodedJWT.getSubject();
            }
            else{
                return null;
            }

        } catch (JWTVerificationException e){
            return e.getMessage();
        }
    }

    private Instant genExpirationDate(){
        return LocalDateTime
                .now()
                .plusHours(12)
                .toInstant(ZoneOffset.of("-3"));
    }
}
