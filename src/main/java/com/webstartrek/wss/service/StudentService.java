package com.webstartrek.wss.service;

import com.webstartrek.wss.models.dtos.request.student.CreateStudentRequest;
import com.webstartrek.wss.models.dtos.response.student.StudentResponse;
import com.webstartrek.wss.models.entities.student.Student;
import java.util.List;

public interface StudentService {
    void createStudent(CreateStudentRequest createStudentRequest);

    StudentResponse readStudentByStudentId(String studentId);

    List<Student> readAllStudents(int pageNumber, int pageSize);

    void updateStudent(Student user);

    void deleteStudentByStudentId(String studentId);

    long countAllStudents();

    List<Student> searchStudentsByColumn(String fieldValue, String column);

    List<Student> searchStudentsByColumn(String fieldValue);
}
