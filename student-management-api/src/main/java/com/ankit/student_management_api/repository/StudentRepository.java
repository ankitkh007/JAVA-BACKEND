package com.ankit.student_management_api.repository;

import com.ankit.student_management_api.model.Student;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class StudentRepository {
    private final Map<Integer, Student> students = new ConcurrentHashMap<>();

    public Collection<Student> findAll() {
        return students.values();
    }

    public Student findById(Integer id) {
        return students.get(id);
    }

    public boolean save(Student student) {
        return students.putIfAbsent(student.getId(), student) == null;
    }

    public Student delete(Integer id) {
        return students.remove(id);
    }
}
