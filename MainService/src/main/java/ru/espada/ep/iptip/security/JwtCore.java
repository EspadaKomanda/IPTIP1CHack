package ru.espada.ep.iptip.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.espada.ep.iptip.config.JWTConfig;
import ru.espada.ep.iptip.user.UserEntity;

import java.util.Date;

@Component
public class JwtCore {

    private final String secret;
    private final long lifetime;

    public JwtCore(@Autowired JWTConfig jwtConfig) {
        secret = jwtConfig.getKey();
        lifetime = jwtConfig.getExpiration();
    }

    public String generateToken(Authentication authentication) {
        UserEntity userDetails = (UserEntity) authentication.getPrincipal();
        System.out.println(new Date());
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + lifetime))
                .signWith(SignatureAlgorithm.HS256, secret).
                compact();
    }

    public String getNameFromJwt(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody().getSubject();
        }catch (Exception e) {
            throw new JwtException("JWT expired");
        }
    }
}
