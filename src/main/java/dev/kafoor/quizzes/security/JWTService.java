package dev.kafoor.quizzes.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JWTService {
    @Value("${token.access.secret}")
    private String accessKey;

    private static final Logger LOGGER = LogManager.getLogger(JWTService.class);

    public Claims getClaimsFromToken(String token){
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(getKey(accessKey))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException expEx) {
            LOGGER.error("Expired JwtException",expEx);
        }catch (UnsupportedJwtException expEx) {
            LOGGER.error("Unsupported JwtException",expEx);
        } catch (MalformedJwtException expEx) {
            LOGGER.error("Malformed JwtException",expEx);
        } catch (SecurityException expEx) {
            LOGGER.error("Security exception",expEx);
        } catch (Exception expEx) {
            LOGGER.error("Invalid token",expEx);
        }
        return null;
    }

    public Date getExpirationDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims != null ? claims.getExpiration() : null;
    }

    public boolean isTokenExpired(String token){
        return getExpirationDateFromToken(token).before(new Date());
    }

    public String getUsernameFromToken(String token){
        return getClaimsFromToken(token).getSubject();
    }

    public boolean validateToken(String token){
        try{
            final String username = getUsernameFromToken(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    private Key getKey(String key){
        byte[] KeyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(KeyBytes);
    }
}