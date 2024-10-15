package com.webstartrek.wss.service;

import com.webstartrek.wss.annotations.AccountDAOIdentifier;
import com.webstartrek.wss.annotations.StudentDAOIdentifier;
import com.webstartrek.wss.dao.DAOService;
import com.webstartrek.wss.mapper.student.util.SudentTestUtil;
import com.webstartrek.wss.models.dtos.request.student.CreateStudentRequest;
import com.webstartrek.wss.models.dtos.response.student.StudentResponse;
import com.webstartrek.wss.models.entities.student.Student;
import com.webstartrek.wss.models.entities.student.StudentAccount;
import com.webstartrek.wss.service.student.StudentNumberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class StudentRecordTest {

    @Mock
    @StudentDAOIdentifier
    private DAOService<Student, String> studentDAO;

    @Mock
    @AccountDAOIdentifier
    private DAOService<StudentAccount, String> accountDAO;

    @Mock
    private StudentNumberService studentNumberService;

    private StudentService studentRecord;

    private Student student;
    private StudentAccount account;
    private CreateStudentRequest createStudentRequest;

    private static final String STUDENT_ID = "KKK-111-222-333";

    @BeforeEach
    void setUp() {
        openMocks(this);

        // Setup mock data
        student = SudentTestUtil.getStudent();
        student.setStudentId(STUDENT_ID);
        createStudentRequest = SudentTestUtil.getCreateStudentRequest();

        studentRecord = new StudentRecord(studentDAO, accountDAO, studentNumberService);
    }

    @Test
    public void testCreateStudent() {
        // Act
        studentRecord.createStudent(createStudentRequest);

        // Assert
        verify(studentDAO, times(1)).create(any(Student.class));
        verify(accountDAO, times(1)).create(any(StudentAccount.class));
    }

    @Test
    public void testReadStudentByStudentId() {

        // Arrange
        when(studentDAO.read(STUDENT_ID)).thenReturn(student);

        // Act
        StudentResponse response = studentRecord.readStudentByStudentId(STUDENT_ID);

        // Assert
        assertNotNull(response);
        assertEquals(STUDENT_ID, response.getStudentId());
        verify(studentDAO, times(1)).read(STUDENT_ID);
    }

    @Test
    public void testReadStudentByStudentId_EntityNotFound() {
        // Arrange
        when(studentDAO.read(STUDENT_ID)).thenThrow(new EntityNotFoundException("Student not found"));

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            studentRecord.readStudentByStudentId(STUDENT_ID);
        });
        assertEquals("Student not found", exception.getMessage());
        verify(studentDAO, times(1)).read(STUDENT_ID);
    }

    @Test
    public void testReadAllStudents() {
        // Arrange
        List<Student> students = new ArrayList<>();
        students.add(student);
        when(studentDAO.readAll(1, 10)).thenReturn(students);

        // Act
        List<Student> result = studentRecord.readAllStudents(1, 10);

        // Assert
        assertEquals(1, result.size());
        verify(studentDAO, times(1)).readAll(1, 10);
    }

    @Test
    public void testUpdateStudent() {
        // Act
        studentRecord.updateStudent(student);

        // Assert
        verify(studentDAO, times(1)).update(student);
    }

    @Test
    public void testDeleteStudentByStudentId() {
        // Act
        studentRecord.deleteStudentByStudentId(STUDENT_ID);

        // Assert
        verify(accountDAO, times(1)).delete(STUDENT_ID);
        verify(studentDAO, times(1)).delete(STUDENT_ID);
    }
}
