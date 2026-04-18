package com.ankit.student_management_api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ankit.student_management_api.dto.PaginatedResponse;
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
    public PaginatedResponse<StudentResponse> getAllStudentsWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));

        Page<Student> studentPage = repository.findAll(pageable);

        // Entity --> Response DTO
        List<StudentResponse> content = new ArrayList<>();
        for (Student student : studentPage.getContent()) { // Use studentPage.getContent() to get the list of students
            content.add(StudentMapper.mapEntityToResponseDTO(student));
        }

        PaginatedResponse<StudentResponse> response = new PaginatedResponse<>();
        response.setContent(content);
        response.setPage(page);
        response.setSize(size);
        response.setTotalElements(studentPage.getTotalElements());
        response.setTotalPages(studentPage.getTotalPages());

        return response;
    }

    // GET student by id
    public StudentResponse getStudentById(Integer id) {
        Student student = repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student Not Found with id: " + id));
        // if (student == null)
        // throw new StudentNotFoundException("Student Not Found with id: " + id);

        // Entity --> Response DTO
        StudentResponse response = StudentMapper.mapEntityToResponseDTO(student);
        return response;
    }

    // GET student by name
    public List<StudentResponse> getStudentByNameCustom(String name) {
        List<Student> students = repository.findByNameCustom(name);
        if (students.isEmpty())
            throw new StudentNotFoundException("Student not found with name: " + name);

        // Entity --> Response DTO
        List<StudentResponse> responseList = new ArrayList<>();
        for (Student student : students) {
            responseList.add(StudentMapper.mapEntityToResponseDTO(student));
        }

        return responseList;
    }

    // Create student (save)
    public StudentResponse createStudent(StudentRequest request) {
        // Request DTO --> Entity
        Student student = StudentMapper.mapRequestDTOToEntity(request);

        if (repository.existsByRoll(student.getRoll()))
            throw new DuplicateStudentException("Student Already Exists with roll: " +
                    request.getRoll());
        Student saved = repository.save(student);
        // if (!created)
        // throw new DuplicateStudentException("Student Already Exists with id: " +
        // request.getId());

        // Entity --> Response DTO
        StudentResponse response = StudentMapper.mapEntityToResponseDTO(saved);
        return response;
    }

    // Delete student
    public void deleteStudent(Integer id) {
        if (!repository.existsById(id))
            throw new StudentNotFoundException("Student Not Found with id: " + id);

        repository.deleteById(id);
    }
}
