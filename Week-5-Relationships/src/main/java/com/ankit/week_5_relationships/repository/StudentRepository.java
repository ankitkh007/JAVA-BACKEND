package com.ankit.week_5_relationships.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ankit.week_5_relationships.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

}