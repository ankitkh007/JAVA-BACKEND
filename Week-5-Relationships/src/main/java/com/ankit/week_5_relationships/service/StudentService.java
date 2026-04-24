package com.ankit.week_5_relationships.service;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.util.*;

import com.ankit.week_5_relationships.dto.CourseRequest;
import com.ankit.week_5_relationships.dto.PaginatedResponse;
import com.ankit.week_5_relationships.dto.StudentBasicResponse;
import com.ankit.week_5_relationships.dto.StudentRequest;
import com.ankit.week_5_relationships.dto.StudentDetailedResponse;
import com.ankit.week_5_relationships.exception.BusinessException;
import com.ankit.week_5_relationships.exception.StudentNotFoundException;
import com.ankit.week_5_relationships.mapper.StudentMapper;
import com.ankit.week_5_relationships.model.Course;
import com.ankit.week_5_relationships.model.Student;
import com.ankit.week_5_relationships.repository.CourseRepository;
import com.ankit.week_5_relationships.repository.StudentRepository;

@Service
public class StudentService {
    private final StudentRepository repository;
    private final CourseRepository courseRepository;

    // Constructor Injection
    public StudentService(StudentRepository repository, CourseRepository cRepository) {
        this.repository = repository;
        this.courseRepository = cRepository;
    }

    // -----------------------------------------------------------------------
    // DETAILED STUDENT RESPONSE
    // Get all students
    public PaginatedResponse<StudentDetailedResponse> getStudentsWithCourses(int page, int size) {
        // First Getting All Students(paginated)
        Pageable pageable = PageRequest.of(page, size);
        Page<Student> studentPage = repository.findAll(pageable);
        List<Student> students = studentPage.getContent();

        // Now extracting student Ids for All Students
        List<Integer> studentIds = students.stream().map(Student::getId).toList();

        // Edge-case handling; what is students list is empty
        if (studentIds.isEmpty())
            return new PaginatedResponse<>(List.of(), page, size, studentPage.getTotalElements(),
                    studentPage.getTotalPages());

        // Secondly getting all courses for each student Id
        List<Course> courses = courseRepository.findByStudentIds(studentIds);

        // Now grouping Courses by student
        Map<Integer, List<Course>> courseMap = new HashMap<>();
        for (Course course : courses) {
            Integer studentId = course.getStudent().getId();

            courseMap.computeIfAbsent(studentId, k -> new ArrayList<>()).add(course);
        }

        // Now attaching course with respective student Id
        for (Student student : students) {
            List<Course> studentCourse = courseMap.get(student.getId());

            student.setCourses(studentCourse != null ? studentCourse : new ArrayList<>());
        }

        // Entity --> Response DTO
        List<StudentDetailedResponse> responseList = students.stream().map(StudentMapper::mapEntityToDetailed)
                .toList();

        return new PaginatedResponse<>(responseList, page, size, studentPage.getTotalElements(),
                studentPage.getTotalPages());
    }

    // -----------------------------------------------------------------------------------------
    // BASIC STUDENT RESPONSE
    public PaginatedResponse<StudentBasicResponse> getBasicStudents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Student> studentPage = repository.findAll(pageable);
        List<Student> students = studentPage.getContent();

        List<StudentBasicResponse> responseList = students.stream().map(StudentMapper::mapEntityToBasic).toList();

        return new PaginatedResponse<>(responseList, page, size, studentPage.getTotalElements(),
                studentPage.getTotalPages());
    }

    // Get student By Id
    public StudentDetailedResponse getStudentById(Integer id) {
        Student student = repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));
        // Entity --> Response DTO
        StudentDetailedResponse response = StudentMapper.mapEntityToDetailed(student);
        return response;
    }

    // Create student
    public StudentDetailedResponse createStudent(StudentRequest request) {
        // validating duplicate courses
        if (!validateCourses(request.getCourses()))
            throw new BusinessException("Duplicate course title not allowed");
        else if (request.getCourses().size() > 5)
            throw new BusinessException("Maximum 5 courses allowed");

        // Request DTO --> Entity
        Student student = StudentMapper.mapRequestDtoToEntity(request);
        Student saved = repository.save(student);
        // Entity --> Response DTO
        StudentDetailedResponse response = StudentMapper.mapEntityToDetailed(saved);
        return response;
    }

    // Attach course to a student
    public StudentDetailedResponse attachCourse(Integer id, CourseRequest courseRequest) {
        Student student = repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));

        Course course = new Course();
        course.setTitle(courseRequest.getTitle());
        course.setStudent(student);

        if (student.getCourses() == null) // null safety
            student.setCourses(new ArrayList<>());
        student.getCourses().add(course);

        Student saved = repository.save(student); // Very Important line

        StudentDetailedResponse response = StudentMapper.mapEntityToDetailed(saved);

        return response;
    }

    // validates Duplicate course title for Request
    private boolean validateCourses(List<CourseRequest> courses) {
        Set<String> titles = new HashSet<>();

        for (CourseRequest courseTitle : courses) {
            if (!titles.add(courseTitle.getTitle().toLowerCase()))
                return false;
        }
        return true;
    }
}
