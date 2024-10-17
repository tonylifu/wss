package com.webstartrek.wss.controller.managedbeans.student;

import com.webstartrek.wss.models.entities.student.Student;
import com.webstartrek.wss.service.StudentService;
import lombok.Getter;
import lombok.Setter;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class StudentBean {

    private final StudentService studentService;

    @Inject
    public StudentBean(StudentService studentService) {
        this.studentService = studentService;
    }

    @Getter
    private List<Student> students;

    @Setter
    @Getter
    private int pageNumber = 1;

    @Setter
    @Getter
    private int pageSize = 10;

    @Getter
    private long totalStudents;

    @PostConstruct
    public void init() {
        loadStudents();
    }

    public void loadStudents() {
        totalStudents = studentService.countAllStudents();
        students = studentService.readAllStudents(pageNumber, pageSize);
    }

    public void nextPage() {
        if ((long) pageNumber * pageSize < totalStudents) {
            pageNumber++;
            loadStudents();
        }
    }

    public void previousPage() {
        if (pageNumber > 1) {
            pageNumber--;
            loadStudents();
        }
    }

}

