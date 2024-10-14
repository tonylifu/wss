package com.webstartrek.wss.dao.student;

import com.webstartrek.wss.models.entities.student.Student;

import java.util.List;

public interface StudentDAO {

    void create(Student student);

    Student read(String studentId);

    List<Student> readAll(int pageNumber, int pageSize);

    void update(Student student);

    void delete(String studentId);
}