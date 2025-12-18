package org.ilmi.expensefulserver.security.helper;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.ilmi.expensefulserver.domain.User;
import org.ilmi.expensefulserver.security.ExpensefulUserDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtHelper {
    @Value("${jwt.secret.key}")
    public String SIGNING_KEY;

    public static final long ACCESS_TOKEN_VALIDITY = 24 * 60 * 60 * 1000; // 1 day
    public static final long ID_TOKEN_VALIDITY = 24 * 60 * 60 * 1000; // 1 day
    public static final long REFRESH_TOKEN_VALIDITY = 7 * 24 * 60 * 60 * 1000; // 7 days

    public final ExpensefulUserDetailsService userDetailService;

    public JwtHelper(ExpensefulUserDetailsService userDetailService) {
        this.userDetailService = userDetailService;
    }

    public String getSubjectFromToken(String token) {

        return getClaimFromToken(token, Claims::getSubject);

    }

    public Key getSigningKey() {
        Base64.Encoder encoder = Base64.getEncoder();

        // Convert the string to bytes using UTF-8 encoding
        byte[] originalBytes = SIGNING_KEY.getBytes(StandardCharsets.UTF_8);

        // Encode the bytes to a Base64 string
        String encodedKey = encoder.encodeToString(originalBytes);

        byte[] keyBytes = Decoders.BASE64.decode(encodedKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateAccessToken(User authenticatedUser) {

        return Jwts.builder()
                .setSubject(authenticatedUser.getId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY * 1000))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateIdToken(User authenticatedUser) {

        return Jwts.builder()
                .setSubject(authenticatedUser.getId())
                .claim("email", authenticatedUser.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ID_TOKEN_VALIDITY * 1000))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(User authenticatedUser) {

        return Jwts.builder()
                .setSubject(authenticatedUser.getId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY * 1000))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parse(token);

            return true;
        } catch (RuntimeException ex) {
            return false;
        }
    }

    public UsernamePasswordAuthenticationToken getAuthenticationToken(
            final Authentication authentication,
            final UserDetails userDetails
    ) {
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

}
