package com.ankit.week_5_relationships.mapper;

import java.util.*;

import com.ankit.week_5_relationships.dto.CourseRequest;
import com.ankit.week_5_relationships.dto.CourseResponse;
import com.ankit.week_5_relationships.dto.StudentBasicResponse;
import com.ankit.week_5_relationships.dto.StudentRequest;
import com.ankit.week_5_relationships.dto.StudentDetailedResponse;
import com.ankit.week_5_relationships.model.Course;
import com.ankit.week_5_relationships.model.Student;

public class StudentMapper {
    public static Student mapRequestDtoToEntity(StudentRequest request) {
        Student student = new Student();
        student.setName(request.getName());
        List<Course> courses = new ArrayList<>();
        if (request.getCourses() != null) {
            for (CourseRequest courseRequest : request.getCourses()) {
                Course course = new Course();
                course.setTitle(courseRequest.getTitle());
                course.setStudent(student);

                courses.add(course);
            }
        }
        student.setCourses(courses);

        return student;
    }

    public static StudentDetailedResponse mapEntityToDetailed(Student student) {
        StudentDetailedResponse response = new StudentDetailedResponse();

        response.setId(student.getId());
        response.setName(student.getName());
        List<CourseResponse> courseList = new ArrayList<>();
        if (student.getCourses() != null) {
            for (Course course : student.getCourses()) {
                CourseResponse courseResponse = new CourseResponse();
                courseResponse.setId(course.getId());
                courseResponse.setTitle(course.getTitle());

                courseList.add(courseResponse);
            }
        }
        response.setCourses(courseList);

        return response;
    }

    public static StudentBasicResponse mapEntityToBasic(Student student) {
        StudentBasicResponse response = new StudentBasicResponse();

        response.setId(student.getId());
        response.setName(student.getName());
        return response;
    }

}
