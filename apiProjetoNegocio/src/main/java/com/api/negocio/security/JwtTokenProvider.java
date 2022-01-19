package com.api.negocio.security;

import com.api.negocio.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private String duration = "30";
    private String key = "cXdlcnR5";

    public String createToken(Usuario usuario){
        long durationStr = Long.valueOf(duration);
        LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(durationStr);
        Date data = Date.from(expirationDate.atZone(ZoneId.systemDefault()).toInstant());
        return Jwts
                .builder()
                .setSubject(usuario.getEmail())
                .setExpiration(data)
                .signWith( SignatureAlgorithm.HS512, key)
                .compact();
    }

    public Claims parseToken(@NonNull String token) {
        return Jwts
                .parser()
                .setSigningKey(this.key)
                .parseClaimsJws(token)
                .getBody();
    }
    public boolean validToken(String token){
        try{
            Claims claims = parseToken(token);
            Date expirationDate = claims.getExpiration();
            return !LocalDateTime.now().isAfter(expirationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }catch(Exception e){
            return false;
        }
    }

    public String getLoggedUser(String token) throws ExpiredJwtException{
        return parseToken(token).getSubject();
    }



}
