package com.anthony.product.util;

import com.anthony.product.model.dto.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtUtils {

    /**
     * custom SHA_256 secretKey from config property.
     */
    @Value("${app.jwt.Secret}")
    private String jwtSecret;
    @Value("${app.jwt.ExpirationMs}")
    private int jwtExpirationMs;

    private SecretKey secretKey;

    /**
     * minimum SHA_256 secretKey string length.
     */
    private static final int SHA_256_SECRET_CHAR_SIZE = 256 / 8;

    @PostConstruct
    public void init() {
        //use default secretKey for SHA-256
        if (jwtSecret == null) {
            this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        } else {
            //use custom secretKey
            int size = jwtSecret.length();
            int left = SHA_256_SECRET_CHAR_SIZE - size;
            if (left > 0) {
                //character for padding
                StringBuilder stringBuilder = new StringBuilder(jwtSecret);
                for (int i = 0; i < left; i++) {
                    stringBuilder.append(i % 10);
                }
                this.secretKey = Keys.hmacShaKeyFor(stringBuilder.toString().getBytes());
            } else {
                this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
            }
        }
    }

    /**
     * Create token.
     *
     * @param authentication auth info
     * @return token
     */
    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        Map<String, Object> claims = new HashMap<>();
        Collection<? extends GrantedAuthority> roles = userPrincipal.getAuthorities();
        if (roles.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            claims.put("isAdmin", true);
        }
        if (roles.contains(new SimpleGrantedAuthority("ROLE_USER"))) {
            claims.put("isUser", true);
        }
        if (roles.contains(new SimpleGrantedAuthority("ROLE_MODERATOR"))) {
            claims.put("isModerator", true);
        }

        return Jwts.builder()
                .setClaims(claims)
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Get username Information from jwt token.
     *
     * @param token token
     * @return auth info
     */
    public String getUserNameFromJwtToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    /**
     * validate token.
     *
     * @param authToken - jwt token
     * @return whether valid
     */
    public boolean validateJwtToken(String authToken, HttpServletRequest request) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(authToken);
            return true;
        } catch (SecurityException ex) {
            request.setAttribute("exception", ex);
            log.error("Invalid JWT signature trace: {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            request.setAttribute("exception", ex);
            log.error("Invalid JWT token: {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            request.setAttribute("exception", ex);
            log.error("JWT token is expired: {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            request.setAttribute("exception", ex);
            log.error("JWT token is unsupported: {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            request.setAttribute("exception", ex);
            log.error("JWT claims string is empty: {}", ex.getMessage());
        }
        return false;
    }
}
