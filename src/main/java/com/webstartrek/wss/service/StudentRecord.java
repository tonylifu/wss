package com.webstartrek.wss.service;

import com.webstartrek.wss.dao.student.StudentDAO;
import com.webstartrek.wss.models.entities.student.Student;
import lombok.RequiredArgsConstructor;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
@RequiredArgsConstructor
public class StudentRecord implements StudentService {
    private final StudentDAO studentDAO;

    @Override
    public void createStudent(Student student) {
        studentDAO.create(student);
    }

    @Override
    public Student readStudentByStudentId(String studentId) {
        return studentDAO.read(studentId);
    }

    @Override
    public List<Student> readAllStudents(int pageNumber, int pageSize) {
        return studentDAO.readAll(pageNumber, pageSize);
    }

    @Override
    public void updateStudent(Student student) {
        studentDAO.update(student);
    }

    @Override
    public void deleteStudentByStudentId(String studentId) {
        studentDAO.delete(studentId);
    }
}
