package uk.ac.ncl.tastetracker.config;

import uk.ac.ncl.tastetracker.config.jwt.JWTFilter;
import uk.ac.ncl.tastetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;



/**
  * The SecurityConfig class is responsible for configuring Spring Security to authenticate and authorize incoming requests based on the defined rules.
  * It defines the user details service, password encoder, authentication provider, and security filter chain.
  *
  * @author Surendhar Chandran Jayapal
  */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * UserRepository to interact with the User entity in the database.
     */
    private final UserRepository userRepository;

    /**
     * JWTFilter dependency for intercepting incoming HTTP requests.
     */
    private final JWTFilter jwtFilter;


    /**
     * Constructs SecurityConfig with the specified dependencies.
     *
     * @param userRepository The UserRepository instance used to retrieve user information from the database.
     * @param jwtFilter The JWTFilter instance used to intercept and validate incoming requests with JWT tokens
     */
    @Autowired
    public SecurityConfig(UserRepository userRepository, JWTFilter jwtFilter){
        this.userRepository = userRepository;
        this.jwtFilter = jwtFilter;
    }



    /**
     * Creates a new instance of CustomUserDetailsService using the UserRepository instance.
     *
     * @return The CustomUserDetailsService instance.
     */
    @Bean
    public UserDetailsService userDetailsService(){
        return new CustomUserDetailsService(userRepository);
    }



    /**
     * Configures the HttpSecurity instance to define the authentication and authorization rules.
     *
     * @param httpSecurity The HttpSecurity instance to configure.
     *
     * @throws Exception If an error occurs while configuring the HttpSecurity instance.
     *
     * @return The SecurityFilterChain instance created from the configured HttpSecurity instance.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.cors()
                .and()
                                .authorizeHttpRequests()
                                .requestMatchers(HttpMethod.POST, "/food_finder/restaurants/reviews/**").authenticated()
                                .requestMatchers("food_finder/users/register","/food_finder/users/login", "/food_finder/users/logout" ).permitAll()
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



    /**
     * Creates a new instance of BCryptPasswordEncoder used to encode passwords.
     *
     * @return BCryptPasswordEncoder instance
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }



    /**
     * Creates a new instance of DaoAuthenticationProvider used to authenticate users with the CustomUserDetailsService instance.
     *
     * @return The DaoAuthenticationProvider instance.
     */
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authenticationProvider;
    }



    /**
     * Creates a new instance of CorsConfigurationSource that enables the server to connect with the client
     * and make API calls.
     *
     * @return CorsConfigurationSource instance
     */
    @Bean
     public CorsConfigurationSource corsConfigurationSource() {
         CorsConfiguration configuration = new CorsConfiguration();
         configuration.setAllowedOrigins(Arrays.asList("*"));
         configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
         configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));

         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
         source.registerCorsConfiguration("/**", configuration);

         return source;
     }


}
