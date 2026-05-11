package com.ankit.week_5_relationships.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ankit.week_5_relationships.repository.AdminDetailsRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    AdminDetailsRepository adminDetailsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return adminDetailsRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with this username"));
    }
}
