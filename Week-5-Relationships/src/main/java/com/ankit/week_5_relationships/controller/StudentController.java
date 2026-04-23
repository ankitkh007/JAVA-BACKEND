package com.ankit.week_5_relationships.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ankit.week_5_relationships.dto.ApiResponse;
import com.ankit.week_5_relationships.dto.CourseRequest;
import com.ankit.week_5_relationships.dto.PaginatedResponse;
import com.ankit.week_5_relationships.dto.StudentBasicResponse;
import com.ankit.week_5_relationships.dto.StudentRequest;
import com.ankit.week_5_relationships.dto.StudentDetailedResponse;
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
    public ResponseEntity<ApiResponse<PaginatedResponse<?>>> getAllStudents(@RequestParam int page,
            @RequestParam int size,
            @RequestParam boolean includeCourses) {

        if (includeCourses) {
            PaginatedResponse<StudentDetailedResponse> response = service.getStudentsWithCourses(page, size);

            return ResponseEntity.ok(new ApiResponse<>(200, "Students fetched successfully", response));
        } else {
            PaginatedResponse<StudentBasicResponse> response = service.getBasicStudents(page, size);
            return ResponseEntity.ok(new ApiResponse<>(200, "Students fetched Successfully", response));
        }
    }

    // Get Student By Id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDetailedResponse>> getStudentById(@PathVariable("id") Integer id) {
        StudentDetailedResponse response = service.getStudentById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Student fetched with id: " + id, response)); // 200 OK
    }

    // Create Student(Save)
    @PostMapping
    public ResponseEntity<ApiResponse<StudentDetailedResponse>> createStudent(
            @Valid @RequestBody StudentRequest request) {
        StudentDetailedResponse response = service.createStudent(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(201, "Student Created Successfully", response)); // 201 CREATED
    }

    @PostMapping("/{id}/courses")
    public ResponseEntity<ApiResponse<StudentDetailedResponse>> attachCourse(@PathVariable("id") Integer id,
            @Valid @RequestBody CourseRequest request) {
        StudentDetailedResponse response = service.attachCourse(id, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(201, "Course attached successfully", response));
    }
}
