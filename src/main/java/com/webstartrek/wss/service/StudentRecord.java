package com.webstartrek.wss.service;

import com.webstartrek.wss.annotations.AccountDAOIdentifier;
import com.webstartrek.wss.annotations.StudentDAOIdentifier;
import com.webstartrek.wss.dao.DAOService;
import com.webstartrek.wss.mapper.student.CreateStudentRequestToStudentMapper;
import com.webstartrek.wss.mapper.student.StudentToStudentResponseMapper;
import com.webstartrek.wss.models.dtos.request.student.CreateStudentRequest;
import com.webstartrek.wss.models.dtos.response.student.StudentResponse;
import com.webstartrek.wss.models.entities.student.Student;
import com.webstartrek.wss.models.entities.student.StudentAccount;
import com.webstartrek.wss.models.enums.StudentStatus;
import com.webstartrek.wss.service.student.StudentNumberService;
import com.webstartrek.wss.util.AppUtil;
import lombok.extern.slf4j.Slf4j;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;

@Stateless
@Slf4j
public class StudentRecord implements StudentService {

    private final DAOService<Student, String> studentDAO;
    private final DAOService<StudentAccount, String> accountDAO;
    private final StudentNumberService studentNumberService;

    @Inject
    public StudentRecord(@StudentDAOIdentifier DAOService<Student, String> studentDAO,
                         @AccountDAOIdentifier DAOService<StudentAccount, String> accountDAO,
                         StudentNumberService studentNumberService) {
        this.studentDAO = studentDAO;
        this.accountDAO = accountDAO;
        this.studentNumberService = studentNumberService;
    }

    @Override
    @Transactional
    public void createStudent(CreateStudentRequest createStudentRequest) {
        log.info("I Got here");
        Student student = createStudentEntity(createStudentRequest);
        StudentAccount accountBalance = createAccountBalanceEntity(student);
        studentDAO.create(student);
        accountDAO.create(accountBalance);
        log.info("I Got here");
    }

    @Override
    public StudentResponse readStudentByStudentId(String studentId) {
        return StudentToStudentResponseMapper.INSTANCE.toStudentResponse(studentDAO.read(studentId));
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
    @Transactional
    public void deleteStudentByStudentId(String studentId) {
        accountDAO.delete(studentId);
        studentDAO.delete(studentId);
    }

    @Override
    public long countAllStudents() {
        return studentDAO.countAll(Student.class);
    }

    /**
     * Creates a new Student entity based on the provided CreateStudentRequest.
     *
     * @param createStudentRequest The CreateStudentRequest containing the information to create the Student entity.
     * @return The newly created Student entity populated with data from the CreateStudentRequest.
     */
    private Student createStudentEntity(CreateStudentRequest createStudentRequest) {
        Student student = CreateStudentRequestToStudentMapper.INSTANCE.toStudent(createStudentRequest);

        // Set the creation and last update timestamps to the current time
        String nextStudentId = AppUtil.generateStudentId(Year.now().getValue(),
                studentNumberService.generateNextStudentId(Year.now().getValue()));
        student.setStudentId(nextStudentId);
        long now = AppUtil.convertLocalDateToLong(LocalDate.now());
        student.setCreatedAt(now);
        student.setLastUpdateAt(now);
        student.setStudentStatus(StudentStatus.CREATED);

        return student;
    }


    /**
     * Creates a new AccountBalance entity based on the provided Student.
     *
     * @param student The Student object for which the AccountBalance entity will be created.
     * @return The newly created AccountBalance entity initialized with default values and information from the Student.
     */
    private StudentAccount createAccountBalanceEntity(Student student) {
        return StudentAccount.builder()
                .studentId(student.getStudentId())
                .balance(BigDecimal.ZERO)
                .createdAt(student.getCreatedAt())
                .lastUpdateAt(student.getLastUpdateAt())
                .lastActionBy(student.getActionBy())
                .build();
    }
}
