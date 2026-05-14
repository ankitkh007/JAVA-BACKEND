package com.ankit.week_5_relationships.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ankit.week_5_relationships.dto.AuthRequest;
import com.ankit.week_5_relationships.util.JWTUtil;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JWTUtil jwtUtil;

    @PostMapping("/students/authenticate")
    public String generateTokens(@RequestBody AuthRequest authRequest) {
        // System.out.println(authRequest.getUsername()); -->used for debigging
        // System.out.println(authRequest.getPassword());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                            authRequest.getPassword()));
            // Once user verified,Now we need to generate jwt token --> for that we need JWT
            // Utility class and jjwt library

            return jwtUtil.generateTokens(authRequest.getUsername());
        } catch (Exception e) {
            throw e;
        }
    }
}
