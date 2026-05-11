package com.ankit.week_5_relationships.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ankit.week_5_relationships.model.Admin;

@Repository
public interface AdminDetailsRepository extends JpaRepository<Admin, Integer> {
    Optional<Admin> findByUsername(String username);
}
