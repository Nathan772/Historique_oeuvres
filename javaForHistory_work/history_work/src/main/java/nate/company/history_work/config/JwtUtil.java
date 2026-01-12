package nate.company.history_work.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import nate.company.history_work.entity.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

/*

this class is made to create authentification tokens


 */
@Component
public class JwtUtil {

    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Clé secrète pour signer le token

    @Value("${jwt.expirationMs:3600000}") // Durée d'expiration du token (configurable)
    private long expirationMs;

    /**
     * Génère un token JWT avec le nom d'utilisateur comme sujet.
     */
    public Token generateToken(String username) {
        var currentDate =System.currentTimeMillis();
        return new Token(Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(currentDate))
                .setExpiration(new Date(currentDate + expirationMs))
                .signWith(secretKey)
                .compact(), currentDate + expirationMs);
    }

    /**
     * Extrait le nom d'utilisateur du token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Vérifie si le token est valide en comparant le nom d'utilisateur et la date d'expiration.
     */
    public boolean validateToken(String token, String username) {
        return username.equals(extractUsername(token)) && !isTokenExpired(token);
    }

    /**
     * Vérifie si le token est expiré.
     */
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    /**
     * Extrait une information spécifique du token.
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }
}

