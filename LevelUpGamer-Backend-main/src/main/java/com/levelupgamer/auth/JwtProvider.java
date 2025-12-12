package com.levelupgamer.auth;

import com.levelupgamer.users.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtProvider {
    // ⚠️ CAMBIO FINAL: Esta es una clave Base64 válida y segura de 512 bits.
    // No la modifiques ni le agregues espacios.
    private final String jwtSecret = "KbW9sX9kL4pQ3rV7tZ2nC6xJ8mH1dG5fB0yA3wE7uS2oI4vR9lK5jM8nN1bV6cX0zQ2wE4rT7yU3iO9pL5kJ8hG2fD1sA0mX";

    private final long jwtExpirationMs = 86400000; // 1 día

    public String generateToken(Usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getCorreo())
                .claim("rol", usuario.getRol().name())
                .claim("usuarioId", usuario.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }
}