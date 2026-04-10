package com.ankit.student_management_api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ankit.student_management_api.service.StudentService;

import jakarta.validation.Valid;

import com.ankit.student_management_api.dto.StudentRequest;
import com.ankit.student_management_api.dto.StudentResponse;

import java.util.*;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    // GET all students
    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAll() {
        List<StudentResponse> responseList = service.getAllStudents();
        return ResponseEntity.ok(responseList); // 200 OK
    }

    // GET student by Id
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getStudentById(@PathVariable("id") Integer id) {
        StudentResponse response = service.getStudentById(id);
        return ResponseEntity.ok(response); // 200 OK
    }

    // Create Student (Save)
    @PostMapping
    public ResponseEntity<StudentResponse> createStudent(@Valid @RequestBody StudentRequest request) {
        StudentResponse response = service.createStudent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response); // 201 CREATED
    }

    // Delete Student
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable("id") Integer id) {
        service.deleteStudent(id);
        return ResponseEntity.noContent().build(); // 204 NO CONTENT
    }
}
