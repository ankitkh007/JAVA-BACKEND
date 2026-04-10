package com.ankit.student_management_api.mapper;

import com.ankit.student_management_api.dto.StudentRequest;
import com.ankit.student_management_api.dto.StudentResponse;
import com.ankit.student_management_api.model.Student;

public class StudentMapper {
    // Common mapper method that maps Request DTO to Entity
    public static Student mapRequestDTOToEntity(StudentRequest request) {
        // Request DTO --> Entity
        Student student = new Student();
        student.setId(request.getId());
        student.setRoll(request.getRoll());
        student.setName(request.getName());
        student.setBranch(request.getBranch());
        student.setSection(request.getSection());

        return student;
    }

    // Common mapper method that maps Entity to Response DTO
    public static StudentResponse mapEntityToResponseDTO(Student student) {
        StudentResponse response = new StudentResponse();
        response.setId(student.getId());
        response.setRoll(student.getRoll());
        response.setName(student.getName());
        response.setBranch(student.getBranch());
        response.setSection(student.getSection());

        return response;
    }
}
