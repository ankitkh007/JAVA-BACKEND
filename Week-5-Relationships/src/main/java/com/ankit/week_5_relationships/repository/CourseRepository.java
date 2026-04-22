package com.ankit.week_5_relationships.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ankit.week_5_relationships.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    @Query("SELECT c FROM Course c WHERE c.student.id IN :studentIds")
    List<Course> findByStudentIds(@Param("studentIds") List<Integer> studentIds);
}
