package com.ankit.student_management_api.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ankit.student_management_api.dto.StudentRequest;
import com.ankit.student_management_api.dto.StudentResponse;
import com.ankit.student_management_api.exception.DuplicateStudentException;
import com.ankit.student_management_api.exception.StudentNotFoundException;
import com.ankit.student_management_api.model.Student;
import com.ankit.student_management_api.repository.StudentRepository;
import com.ankit.student_management_api.mapper.*;

@Service
public class StudentService {
    private final StudentRepository repository;

    // Constructor Injection
    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    // GET all students
    public List<StudentResponse> getAllStudents() {
        Collection<Student> students = repository.findAll();

        // Entity --> Response DTO
        List<StudentResponse> responseList = new ArrayList<>();
        for (Student student : students) {
            responseList.add(StudentMapper.mapEntityToResponseDTO(student));
        }
        return responseList;
    }

    // GET student by id
    public StudentResponse getStudentById(Integer id) {
        Student student = repository.findById(id);
        if (student == null)
            throw new StudentNotFoundException("Student Not Found with id: " + id);
        // Entity --> Response DTO
        StudentResponse response = StudentMapper.mapEntityToResponseDTO(student);
        return response;
    }

    // Create student (save)
    public StudentResponse createStudent(StudentRequest request) {
        // Request DTO --> Entity
        Student student = StudentMapper.mapRequestDTOToEntity(request);

        boolean created = repository.save(student);
        if (!created)
            throw new DuplicateStudentException("Student Already Exists with id: " + request.getId());

        // Entity --> Response DTO
        StudentResponse response = StudentMapper.mapEntityToResponseDTO(student);
        return response;
    }

    // Delete student
    public void deleteStudent(Integer id) {
        Student student = repository.delete(id);
        if (student == null)
            throw new StudentNotFoundException("Student Not Found with id: " + id);
    }
}
