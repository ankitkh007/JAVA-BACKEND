package com.ankit.week_5_relationships.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import static org.springframework.security.config.Customizer.withDefaults;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // disable for now
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers("/students/public/**").permitAll().anyRequest().authenticated())
                .httpBasic(withDefaults());

        http.addFilterBefore(new CustomLoggingFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public AuthenticationManager authManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider(userDetailsService);
        dao.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(dao);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("ankit").password(passwordEncoder().encode("1234")).roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Component
    public class CustomLoggingFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                FilterChain filterChain) throws ServletException, IOException {

            // Testing Response time
            long start_time = System.currentTimeMillis();
            try {
                filterChain.doFilter(request, response);
            } finally {
                long duration = System.currentTimeMillis() - start_time;
                System.out.println("Request API: " + request.getRequestURI() + " | Method: " + request.getMethod()
                        + " | Time: " + duration);
            }
            // System.out.println("Request intercepted: " + request.getRequestURI());
            // filterChain.doFilter(request, response);
        }
    }

}
