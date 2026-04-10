package com.ankit.week_2_backend_demo_spring.service;

import com.ankit.week_2_backend_demo_spring.dto.*;
import com.ankit.week_2_backend_demo_spring.exception.DuplicateStudentException;
import com.ankit.week_2_backend_demo_spring.exception.StudentNotFoundException;
import com.ankit.week_2_backend_demo_spring.mapper.StudentMapper;
import com.ankit.week_2_backend_demo_spring.model.Student;
import com.ankit.week_2_backend_demo_spring.repository.StudentRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class StudentService {
    private final StudentRepository repository;

    // Constructor Injection
    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    // Get All Students
    public List<StudentResponse> getAllStudents() {
        Collection<Student> students = repository.findAll();

        // Entity --> List of DTOs
        List<StudentResponse> responseList = new ArrayList<>();

        for (Student student : students) {
            responseList.add(StudentMapper.mapToResponseDTO(student));
        }
        return responseList;
    }

    // Get Student by Id
    public StudentResponse getStudent(int id) {
        Student student = repository.findById(id);
        if (student == null)
            throw new StudentNotFoundException("Student not found with id: " + id);

        // Entity --> DTO
        return StudentMapper.mapToResponseDTO(student);
    }

    // Create Student(Save)
    public StudentResponse createStudent(StudentRequest request) {

        // DTO --> Entity
        Student student = StudentMapper.mapRequestDTOtoEntity(request);
        boolean created = repository.save(student);
        if (!created)
            throw new DuplicateStudentException("Student already exists with id: " + request.getId());

        // Entity --> DTO
        return StudentMapper.mapToResponseDTO(student);
    }

    // Delete Student
    public void deleteStudent(int id) {
        Student student = repository.delete(id);
        if (student == null)
            throw new StudentNotFoundException("Student not found with id: " + id);
    }

}
