package com.app.foodfinder.config.jwt;

import com.app.foodfinder.config.UserDetailsServiceImplementation;
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
import java.io.IOException;


/**
 * A filter that intercepts incoming HTTP requests and processes any JWT tokens found in their
 * Authorization header. If a valid token is found, it is used to authenticate the user and set
 * their authentication context in the SecurityContextHolder.
 *
 * @author CSC8019_Team 15
 * @since 2023-05-01
 */
@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserDetailsServiceImplementation userDetailsService;



    /**
     * Constructs a new JWTFilter with the specified dependencies.
     *
     * @param jwtService the JWTService to use for token validation and generation
     * @param userDetailsService the UserDetailsServiceImplementation to use for user authentication
     */
    @Autowired
    public JWTFilter(JWTService jwtService, UserDetailsServiceImplementation userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
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

        if(authorization != null && authorization.startsWith("Bearer "))
        {
            token = authorization.substring(7);
            username = jwtService.getUsernameFromToken(token);
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if(jwtService.validateToken(token, userDetails))
            {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }


}
