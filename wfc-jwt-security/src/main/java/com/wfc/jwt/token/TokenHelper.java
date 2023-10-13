package com.wfc.jwt.token;

import com.wfc.jwt.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author hui.guo
 * @since 2023/7/6 10:15 上午
 */
@RequiredArgsConstructor
public class TokenHelper {

    private final JwtProperties jwtProperties;

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String AUDIENCE_WEB = "web";
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    public String getUserNameFromToken(String token) {
        final Claims claims = this.getAllClaimsFromToken(token);
        if (claims == null) {
            return null;
        }

        return claims.getSubject();
    }

    public String refreshToken(String token) {
        final Claims claims = this.getAllClaimsFromToken(token);
        if (claims == null) {
            return null;
        }

        claims.setIssuedAt(new Date());

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SIGNATURE_ALGORITHM, jwtProperties.getSecret())
                .compact();
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setIssuer(jwtProperties.getIssuer())
                .setSubject(username)
                .setAudience(AUDIENCE_WEB)
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate())
                .signWith(SIGNATURE_ALGORITHM, jwtProperties.getSecret())
                .compact();
    }

    private Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(jwtProperties.getSecret())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + jwtProperties.getExpiresIn() * 1000);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUserNameFromToken(token);
        return (username != null && username.equals(userDetails.getUsername()));
    }

    public String getToken(HttpServletRequest request) {
        String authHeader = getAuthHeaderFromHeader(request);
        if (authHeader != null && authHeader.startsWith(TOKEN_PREFIX)) {
            return authHeader.substring(7);
        }
        return null;
    }

    private String getAuthHeaderFromHeader(HttpServletRequest request) {
        return request.getHeader(jwtProperties.getHeader());
    }

}
