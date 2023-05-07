package uk.ac.ncl.tastetracker.config.jwt;

import uk.ac.ncl.tastetracker.config.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uk.ac.ncl.tastetracker.exception.custom.InvalidCredentialsException;
import java.io.IOException;



/**
 * A filter that intercepts incoming HTTP requests and processes any JWT found in their
 * Authorization header. If a valid token is found, it is used to authenticate the user and set
 * their authentication context in the SecurityContextHolder.
 *
 * @author Surendhar Chandran Jayapal
 * @version 1.5 (Date - 06-05-2023)
 * @since 1.3 (Date - 28-04-2023)
 */
@Component
public class JWTFilter extends OncePerRequestFilter {


    /**
     * Service for JWT operations
     */
    private final JWTService jwtService;

    /**
     * Service for retrieving user details
     */
    private final CustomUserDetailsService userDetailsService;

    /**
     * Token blacklist for invalidating tokens
     */
    private final TokenBlacklist tokenBlacklist;



    /**
     * Constructs a new JWTFilter with the specified dependencies.
     *
     * @param jwtService the JWTService to use for token validation and generation
     * @param userDetailsService the CustomUserDetailsService to use for user authentication
     */
    @Autowired
    public JWTFilter(JWTService jwtService, CustomUserDetailsService userDetailsService, TokenBlacklist tokenBlacklist) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.tokenBlacklist = tokenBlacklist;
    }





    /**
     * This method processes an incoming HTTP request and sets the user's authentication context if a valid
     * JWT token is found in its Authorization header.
     *
     * @param request the incoming HTTP request to process
     * @param response the HTTP response to send
     * @param filterChain the filter chain to execute
     *
     * @throws ServletException if the filter chain throws a ServletException
     * @throws IOException if the filter chain throws an IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        String token = null;
        String username = null;


        if(authorization != null && authorization.startsWith("Bearer ")) {
            token = authorization.substring(7);
            username = jwtService.getUsernameFromToken(token);
        }

        //Checks if the token is blacklisted before authenticating.
        if (token != null && tokenBlacklist.isTokenBlacklisted(token)) {
            throw new InvalidCredentialsException("Token blacklisted");
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if(jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }



}
