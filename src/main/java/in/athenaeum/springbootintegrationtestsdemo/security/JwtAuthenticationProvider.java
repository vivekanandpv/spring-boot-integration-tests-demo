package in.athenaeum.springbootintegrationtestsdemo.security;

import in.athenaeum.springbootintegrationtestsdemo.util.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final JwtUtils jwtUtils;

    public JwtAuthenticationProvider(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            String token = String.valueOf(authentication.getCredentials());

            if (jwtUtils.validateToken(token)) {
                return new JwtAuthentication(
                        jwtUtils.extractUsername(token),
                        jwtUtils.extractClaim(token, c -> {
                            return ((ArrayList<String>) c.getPayload().get("roles"))
                                    .stream()
                                    .map(SimpleGrantedAuthority::new)
                                    .collect(Collectors.toList());
                        }));
            }
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException |
                 IllegalArgumentException exception) {
            throw new BadCredentialsException("Token invalid");
        }

        // There is an edge case where token is valid, but user might be blocked
        throw new BadCredentialsException("Login failed");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
