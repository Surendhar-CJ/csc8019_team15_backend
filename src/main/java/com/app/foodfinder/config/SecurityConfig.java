package com.app.foodfinder.config;

import com.app.foodfinder.jwt.JWTFilter;
import com.app.foodfinder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserRepository userRepository;

    @Autowired
    private JWTFilter jwtFilter;

    @Autowired
    public SecurityConfig(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Bean
    public UserDetailsService userDetailsService()
    {
        return new UserDetailsServiceImplementation(userRepository);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
    {
        httpSecurity.authorizeHttpRequests()
                                .requestMatchers(HttpMethod.POST, "/food_finder/restaurants/reviews/**").authenticated()
                                .requestMatchers(HttpMethod.PUT, "/food_finder/restaurants/reviews/**").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/food_finder/restaurants/reviews/**").authenticated()
                                .requestMatchers("/food_finder/users/login", "food_finder/users/register").permitAll()
                                .anyRequest().permitAll()

                                .and()

                                .sessionManagement()
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                                .and()
                                .authenticationProvider(authenticationProvider())
                                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        httpSecurity.csrf().disable();

        return httpSecurity.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authenticationProvider;
    }
}
