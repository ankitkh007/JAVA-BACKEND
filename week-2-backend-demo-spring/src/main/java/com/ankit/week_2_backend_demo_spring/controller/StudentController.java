package com.ankit.week_2_backend_demo_spring.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ankit.week_2_backend_demo_spring.service.StudentService;
import com.ankit.week_2_backend_demo_spring.dto.*;

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
    public ResponseEntity<List<StudentResponse>> getAllStudents() { // ResponseEntity added
        List<StudentResponse> responseList = service.getAllStudents();
        return ResponseEntity.ok(responseList);
    }

    // Get Student by Id
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getStudent(@PathVariable("id") int id) { // ResponseEntity added
        StudentResponse response = service.getStudent(id);
        return ResponseEntity.ok(response);
    }

    // Create Student(Save)
    @PostMapping
    public ResponseEntity<StudentResponse> createStudent(@Valid @RequestBody StudentRequest request) { // ResponseEntity
                                                                                                       // added
        StudentResponse response = service.createStudent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response); // 201 Created
    }

    // Delete Student
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable("id") int id) { // ResponseEntity added
        service.deleteStudent(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
