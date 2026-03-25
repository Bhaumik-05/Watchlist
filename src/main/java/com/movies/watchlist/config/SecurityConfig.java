package com.movies.watchlist.config;

// @Configuration tells Spring: this class contains bean definitions
// Beans are objects Spring creates and manages for you
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.Customizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.movies.watchlist.security.CustomUserDetailsService;
import org.springframework.http.HttpMethod;

@Configuration
public class SecurityConfig {

    // Inject our custom service that loads users from the database
    @Autowired
    private CustomUserDetailsService userDetailsService;

    // @Bean means: Spring will manage this object and inject it wherever
    // PasswordEncoder is @Autowired (e.g. in UserService)
    // BCryptPasswordEncoder is the industry standard for password hashing
    // It adds a random "salt" to each hash so two identical passwords
    // produce different hashes — making it very hard to crack
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // SecurityFilterChain defines the security rules for all HTTP requests
    // Every request passes through this "filter chain" before reaching your controller
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF (Cross-Site Request Forgery) protection
                // CSRF is needed for browser-based apps (forms, cookies)
                // Since we're using Postman + Basic Auth (stateless), we don't need it
                .csrf(csrf -> csrf.disable())

                // Define which endpoints need authentication and which are public
                .authorizeHttpRequests(auth -> auth

                        // completely public — no login needed
                        .requestMatchers("/users/register").permitAll()

                        // ADMIN only — managing the movie catalogue
                        .requestMatchers(HttpMethod.POST,   "/movies").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,    "/movies/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/movies/**").hasRole("ADMIN")

                        // USER only — personal watchlist and reviews
                        .requestMatchers(HttpMethod.POST,   "/watchlist").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT,    "/watchlist/**").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/watchlist/**").hasRole("USER")
                        .requestMatchers(HttpMethod.POST,   "/reviews").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/reviews/**").hasRole("USER")

                        // everything else just needs to be logged in (GETs etc.)
                        .anyRequest().authenticated()
                )

                // httpBasic means: use HTTP Basic Authentication
                // In Postman: go to Authorization → Basic Auth → enter username + password
                // Spring will base64-encode them and send in the request header
                // Our CustomUserDetailsService then looks up the user and verifies
                .httpBasic(Customizer.withDefaults())

                // Tell Spring Security to use OUR database-backed service
                // instead of any default in-memory user store
                .userDetailsService(userDetailsService);

        return http.build();
    }
}