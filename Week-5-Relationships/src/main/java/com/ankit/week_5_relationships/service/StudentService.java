package com.ankit.week_5_relationships.service;

import org.springframework.stereotype.Service;
import java.util.*;

import com.ankit.week_5_relationships.dto.CourseRequest;
import com.ankit.week_5_relationships.dto.StudentRequest;
import com.ankit.week_5_relationships.dto.StudentResponse;
import com.ankit.week_5_relationships.exception.StudentNotFoundException;
import com.ankit.week_5_relationships.mapper.StudentMapper;
import com.ankit.week_5_relationships.model.Course;
import com.ankit.week_5_relationships.model.Student;
import com.ankit.week_5_relationships.repository.StudentRepository;

@Service
public class StudentService {
    private final StudentRepository repository;

    // Constructor Injection
    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    // Get all students
    public List<StudentResponse> getAllStudents() {
        List<Student> students = repository.findAll();

        // Entity --> Response DTO
        List<StudentResponse> responseList = new ArrayList<>();
        for (Student student : students) {
            responseList.add(StudentMapper.mapEntityToResponseDto(student));
        }

        return responseList;
    }

    // Get student By Id
    public StudentResponse getStudentById(Integer id) {
        Student student = repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student Not found with id: " + id));
        // Entity --> Response DTO
        StudentResponse response = StudentMapper.mapEntityToResponseDto(student);
        return response;
    }

    // Create student
    public StudentResponse createStudent(StudentRequest request) {
        // Request DTO --> Entity
        Student student = StudentMapper.mapRequestDtoToEntity(request);
        Student saved = repository.save(student);
        // Entity --> Response DTO
        StudentResponse response = StudentMapper.mapEntityToResponseDto(saved);
        return response;
    }

    // Attach course to a student
    public StudentResponse attachCourse(Integer id, CourseRequest courseRequest) {
        Student student = repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student Not found with id: " + id));

        Course course = new Course();
        course.setTitle(courseRequest.getTitle());
        course.setStudent(student);

        if (student.getCourses() == null) // null safety
            student.setCourses(new ArrayList<>());
        student.getCourses().add(course);

        Student saved = repository.save(student); // Very Important line

        StudentResponse response = StudentMapper.mapEntityToResponseDto(saved);

        return response;
    }
}
