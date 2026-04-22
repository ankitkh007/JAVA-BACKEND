package com.ankit.week_5_relationships.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ankit.week_5_relationships.model.Student;
import java.util.*;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    @Query("SELECT s FROM Student s JOIN FETCH s.courses")
    List<Student> findAllWithCourses();
}