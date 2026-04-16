package com.ankit.student_management_api.repository;

import com.ankit.student_management_api.model.Student;
import java.util.*;
//import java.util.concurrent.ConcurrentHashMap;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    boolean existsByRoll(Integer roll);

    @Query("SELECT s FROM Student s WHERE s.name=:name")
    List<Student> findByNameCustom(@Param("name") String name);
}
// private final Map<Integer, Student> students = new ConcurrentHashMap<>();

// public Collection<Student> findAll() {
// return students.values();
// }

// public Student findById(Integer id) {
// return students.get(id);
// }

// public boolean save(Student student) {
// return students.putIfAbsent(student.getId(), student) == null;
// }

// public Student delete(Integer id) {
// return students.remove(id);
// }
