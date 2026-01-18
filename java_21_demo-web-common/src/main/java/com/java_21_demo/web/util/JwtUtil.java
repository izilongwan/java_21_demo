package com.java_21_demo.web.util;

import java.util.Date;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class JwtUtil {
    String key;
    String id;
    String name;
    long ttl;
    Map<String, Object> claims;
    @Builder.Default
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    public String buildJwt() {
        long exp = System.currentTimeMillis() + ttl;

        return Jwts.builder()
                .setId(id)
                .setSubject(name)
                .setIssuedAt(new Date())
                .setExpiration(ttl > 0 ? new Date(exp) : null)
                .signWith(SignatureAlgorithm.HS256, key)
                .addClaims(claims)
                .compact();
    }

    public Claims parseJwt(String token, String key) {
        try {
            Claims parse = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();
            return parse;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Claims parseJwt(String token) {
        return parseJwt(token, key);
    }
}
