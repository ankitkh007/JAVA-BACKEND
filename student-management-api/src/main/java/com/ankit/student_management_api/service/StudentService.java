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

import lombok.extern.slf4j.Slf4j;

import com.ankit.student_management_api.mapper.*;

@Slf4j // This generates: private static final Logger log =
       // LoggerFactory.getLogger(ClassName.class);
@Service
public class StudentService {
    private final StudentRepository repository;

    // Constructor Injection
    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    // GET all students
    public PaginatedResponse<StudentResponse> getAllStudentsWithPagination(int page, int size) {
        // -----LOG INFO-----
        log.info("Fetching students with pagination: page={}, size={}", page, size);

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
        // -----LOG INFO-----
        log.info("Fetching student with id: {}", id);
        if (!repository.existsById(id))
            log.error("Student Not Found with id: {}", id); // -----LOG ERROR-----

        Student student = repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student Not Found with id: " + id));

        // Entity --> Response DTO
        StudentResponse response = StudentMapper.mapEntityToResponseDTO(student);
        return response;
    }

    // GET student by name
    public List<StudentResponse> getStudentByNameCustom(String name) {
        // -----LOG INFO-----
        log.info("Fetching students with name: {}", name);

        List<Student> students = repository.findByNameCustom(name);
        if (students.isEmpty()) {
            log.error("Student Not Found with name: {}", name); // -----LOG ERROR-----
            throw new StudentNotFoundException("Student Not Found with name: " + name);
        }

        // Entity --> Response DTO
        List<StudentResponse> responseList = new ArrayList<>();
        for (Student student : students) {
            responseList.add(StudentMapper.mapEntityToResponseDTO(student));
        }

        return responseList;
    }

    // Create student (save)
    public StudentResponse createStudent(StudentRequest request) {
        // -----LOG INFO-----
        log.info("Creating student with roll: {}", request.getRoll());

        // Request DTO --> Entity
        Student student = StudentMapper.mapRequestDTOToEntity(request);

        if (repository.existsByRoll(student.getRoll())) {
            log.error("Student Already Exists with roll: {}", request.getRoll()); // -----LOG ERROR-----
            throw new DuplicateStudentException("Student Already Exists with roll: " + request.getRoll());
        }
        Student saved = repository.save(student);

        // Entity --> Response DTO
        StudentResponse response = StudentMapper.mapEntityToResponseDTO(saved);
        return response;
    }

    // Delete student
    public void deleteStudent(Integer id) {
        // -----LOG INFO-----
        log.info("Deleting student with id: {}", id);

        if (!repository.existsById(id)) {
            log.error("Student Not Found with id: {}", id); // -----LOG ERROR-----
            throw new StudentNotFoundException("Student Not Found with id: " + id);
        }

        repository.deleteById(id);
    }
}
