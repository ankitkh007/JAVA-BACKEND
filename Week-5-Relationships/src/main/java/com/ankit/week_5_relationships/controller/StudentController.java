package com.ankit.week_5_relationships.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ankit.week_5_relationships.dto.CourseRequest;
import com.ankit.week_5_relationships.dto.StudentRequest;
import com.ankit.week_5_relationships.dto.StudentResponse;
import com.ankit.week_5_relationships.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService service;

    // Constructor Injection
    public StudentController(StudentService service) {
        this.service = service;
    }

    // Get All Students
    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAllStudents() {
        List<StudentResponse> response = service.getAllStudents();
        return ResponseEntity.ok(response); // 200 OK
    }

    // Get Student By Id
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getStudentById(@PathVariable("id") Integer id) {
        StudentResponse response = service.getStudentById(id);
        return ResponseEntity.ok(response); // 200 OK
    }

    // Create Student(Save)
    @PostMapping
    public ResponseEntity<StudentResponse> createStudent(@Valid @RequestBody StudentRequest request) {
        StudentResponse response = service.createStudent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response); // 201 CREATED
    }

    @PostMapping("/{id}/courses")
    public ResponseEntity<StudentResponse> attachCourse(@PathVariable("id") Integer id,
            @Valid @RequestBody CourseRequest request) {
        StudentResponse response = service.attachCourse(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
