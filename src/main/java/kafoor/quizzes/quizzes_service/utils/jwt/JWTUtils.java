package kafoor.quizzes.quizzes_service.utils.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JWTUtils {
    @Value("${token.access.secret}")
    private String accessKey;

    public <T> T getClaimsFromToken(String token, Function<Claims, T> claimsTFunction){
        final Claims claims = getClaimsFromToken(token);
        return claimsTFunction.apply(claims);
    }

    public Claims getClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(getKey(accessKey)).parseClaimsJws(token).getBody();
    }

    public Date getExpirationDateFromToken(String token){
        return getClaimsFromToken(token).getExpiration();
    }

    public boolean isTokenExpired(String token){
        return  getExpirationDateFromToken(token).before(new Date());
    }

    public String getUsernameFromToken(String token){
        return getClaimsFromToken(token).getSubject();
    }

    public boolean validateToken(String token){
        try{
            final String username = getUsernameFromToken(token);
            return isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    private Key getKey(String key){
        byte[] KeyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(KeyBytes);
    }
}