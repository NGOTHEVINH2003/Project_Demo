package com.project.ReservationSystem.Security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.LoggerFactory;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class jwtUtils {
    private static  final Logger logger = LoggerFactory.getLogger(jwtUtils.class);
    @Value("${auth.token.expirationInMils}")
    private int ExpirationTime;

    @Value("${auth.token.jwtSecret}")
    private String Secret;

    public String generateUserJWT(Authentication auth){
        userDetails userPrincipal = (userDetails) auth.getPrincipal();
        List<String> role = userPrincipal.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).toList();

        return Jwts.builder().setSubject(userPrincipal.getUsername())
                .claim("role", role).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + ExpirationTime) )
                .signWith(key(), SignatureAlgorithm.HS256).compact();

    }
    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(Secret));
    }

    public String extractName(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key()).build()
                .parseClaimsJwt(token).getBody().getSubject();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key()).build().parse(token);
            return true;
        }catch (MalformedJwtException e){
            logger.error("Malformed jwt token: {}", e.getMessage());
        }catch (UnsupportedJwtException e){
            logger.error("jwt token is not supported: {}", e.getMessage());
        }catch (ExpiredJwtException e){
            logger.error("jwt token has expired: {}",e.getMessage());
        }catch (IllegalArgumentException e){
            logger.error("No claims found: {}",e.getMessage());
        }
        return  false;
    }
}
