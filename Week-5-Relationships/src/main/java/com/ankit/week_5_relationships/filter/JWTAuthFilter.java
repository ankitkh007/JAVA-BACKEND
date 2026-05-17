package com.ankit.week_5_relationships.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ankit.week_5_relationships.service.CustomUserDetailsService;
import com.ankit.week_5_relationships.util.JWTUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// this filter will come before BasicAuth filter and extract jwt token from request & verify it and set it inside SecurityContextHolder
@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // System.out.println("JWT Filter starts...");
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username = jwtUtil.extractUsername(token);
        }
        // System.out.println("Token: " + token);
        // System.out.println("Extracted username: " + username);

        // validate token for this we need UserDeatils so we need to extract it
        // from token and then validate it
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) { // --> already koi
            // auth present na ho
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            // System.out.println("Authorities: " + userDetails.getAuthorities());
            // System.out.println("validate: " + jwtUtil.validate(username, userDetails,
            // token));
            // System.out.println("Current Auth: " +
            // SecurityContextHolder.getContext().getAuthentication());
            if (jwtUtil.validate(username, userDetails, token)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // this sets request
                                                                                                  // related info with
                                                                                                  // the authToken for
                                                                                                  // SecurityContext
                // 1. Create a fresh context object container
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(authToken);

                // 2. Set it globally to the current thread lifecycle
                SecurityContextHolder.setContext(context);
                // System.out.println(SecurityContextHolder.getContext().getAuthentication());

                // MANDATORY FOR SPRING SECURITY 6: Explicitly save it to the request Attribute
                // repository, for stateless behaviour otherwise Spring sets SecurityContext to
                // AnonymousAuthentication
                RequestAttributeSecurityContextRepository repo = new RequestAttributeSecurityContextRepository();
                repo.saveContext(context, request, response);
            }
        }
        filterChain.doFilter(request, response);

        // System.out.println("Filter completed.");
    }

}
