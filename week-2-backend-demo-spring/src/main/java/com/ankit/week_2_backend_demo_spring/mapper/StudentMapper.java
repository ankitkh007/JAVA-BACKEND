package com.ankit.week_2_backend_demo_spring.mapper;

import com.ankit.week_2_backend_demo_spring.dto.StudentRequest;
import com.ankit.week_2_backend_demo_spring.dto.StudentResponse;
import com.ankit.week_2_backend_demo_spring.model.Student;

public class StudentMapper {
    // Common Mapper method that maps Entity to Response DTO
    public static Student mapRequestDTOtoEntity(StudentRequest request) {
        Student student = new Student();
        student.setId(request.getId());
        student.setRoll(request.getRoll());
        student.setName(request.getName());
        student.setBranch(request.getBranch());
        student.setSection(request.getSection());

        return student;
    }

    // Common Mapper method that maps Entity to Response DTO
    public static StudentResponse mapToResponseDTO(Student student) {
        StudentResponse response = new StudentResponse();
        response.setId(student.getId());
        response.setRoll(student.getRoll());
        response.setName(student.getName());
        response.setBranch(student.getBranch());
        response.setSection(student.getSection());

        return response;
    }
}
