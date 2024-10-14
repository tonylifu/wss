package com.webstartrek.wss.service;

import com.webstartrek.wss.models.entities.student.Student;

import java.util.List;

public interface StudentService {
    void createStudent(Student student);

    Student readStudentByStudentId(String studentId);

    List<Student> readAllStudents(int pageNumber, int pageSize);

    void updateStudent(Student user);

    void deleteStudentByStudentId(String studentId);
}
