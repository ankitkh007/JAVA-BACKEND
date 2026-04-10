package com.ankit.week_2_backend_demo_spring.repository;

import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;
import com.ankit.week_2_backend_demo_spring.model.Student;
import java.util.*;

@Repository
public class StudentRepository {
    private Map<Integer, Student> students = new ConcurrentHashMap<>();

    public Collection<Student> findAll() {
        return students.values();
    }

    public Student findById(int id) {
        return students.get(id);
    }

    public boolean save(Student student) {
        return students.putIfAbsent(student.getId(), student) == null;
    }

    public Student delete(int id) {
        return students.remove(id);
    }
}
