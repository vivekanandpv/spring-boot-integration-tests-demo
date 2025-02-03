package in.athenaeum.springbootintegrationtestsdemo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    private final String serverSecret;

    public JwtUtils(@Value("${app.server.secret}") String serverSecret) {
        this.serverSecret = serverSecret;
    }

    public byte[] getSigningKey() {
        return serverSecret.getBytes(StandardCharsets.UTF_8);
    }

    public String extractUsername(String token) {
        return extractClaim(token, claimsJws -> claimsJws.getPayload().getSubject());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, (claimsJws -> claimsJws.getPayload().getExpiration()));
    }

    public <T> T extractClaim(String token, Function<Jws<Claims>, T> claimsResolver) {
        final Jws<Claims> claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Jws<Claims> extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(this.getSigningKey()))
                .build()
                .parseSignedClaims(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .claims(
                        Map.of(
                                "roles",
                                userDetails
                                        .getAuthorities()
                                        .stream()
                                        .map(GrantedAuthority::getAuthority)
                                        .collect(Collectors.toList())
                        )
                )
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plus(30, ChronoUnit.MINUTES)))
                .signWith(Keys.hmacShaKeyFor(this.getSigningKey()))
                .compact();
    }

    public boolean validateToken(String token) throws io.jsonwebtoken.ExpiredJwtException, io.jsonwebtoken.UnsupportedJwtException, io.jsonwebtoken.MalformedJwtException, io.jsonwebtoken.security.SignatureException, IllegalArgumentException {
        return !isTokenExpired(token);
    }
}
