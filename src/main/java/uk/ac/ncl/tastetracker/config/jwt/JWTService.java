package uk.ac.ncl.tastetracker.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


/**
 * This class provides methods to generate and validate JWT tokens.
 *
 * @author CSC8019_Team 15
 * @since 2023-05-01
 */
@Component
public class JWTService {

    /**
     * The validity period of the JWT token (in seconds).
     */
    public static final long JWTTokenValidity = 10 * 60;


    /**
     * The secret key used to sign the JWT token.
     */
    @Value("${jwt.secret}")
    private String secretKey;



    /**
     * Returns the username from the JWT token.
     *
     * @param token The JWT token.
     * @return The username.
     */
    public String getUsernameFromToken(String token)
    {
        return getClaimFromToken(token, Claims::getSubject);
    }



    /**
     * Returns the expiration date of the JWT token.
     *
     * @param token The JWT token.
     * @return The expiration date.
     */
    public Date getExpirationDateFromToken(String token)
    {
        return getClaimFromToken(token, Claims::getExpiration);
    }



    /**
     * Returns a claim from the JWT token.
     *
     * @param token         The JWT token.
     * @param claimsResolver The function to apply to the claims.
     * @param <T>           The type of the claim.
     * @return The claim.
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }



    /**
     * Returns all the claims from the JWT token.
     *
     * @param token The JWT token.
     * @return The claims.
     */
    public Claims getAllClaimsFromToken(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }



    /**
     * Returns whether the JWT token is expired.
     *
     * @param token The JWT token.
     * @return Whether the JWT token is expired.
     */
    public Boolean isTokenExpired(String token)
    {
        return getExpirationDateFromToken(token).before(new Date());
    }



    /**
     * Validates the JWT token.
     *
     * @param token The JWT token.
     * @param userDetails The user details.
     * @return Whether the JWT token is valid.
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }



    /**
     * Generates a new JWT token for the given username.
     *
     * @param username The username.
     * @return The JWT token.
     */
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }



    /**
     * Returns the signing key used to sign the JWT token.
     *
     * @return The signing key.
     */
    public Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }



    /**
     * Creates a new JWT token for the given claims and username.
     *
     * @param claims   The claims.
     * @param username The username.
     * @return The JWT token.
     */
    private String createToken(Map<String, Object> claims, String username) {
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(username)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis()+ JWTTokenValidity * 1000))
                    .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }




}
