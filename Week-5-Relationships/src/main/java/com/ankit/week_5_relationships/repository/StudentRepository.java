package com.ankit.week_5_relationships.repository;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.ankit.week_5_relationships.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    // @Query("SELECT s FROM Student s JOIN FETCH s.courses")
    // Not required anymore since we are using 2-query approach
    Page<Student> findAll(Pageable pageable);
}
