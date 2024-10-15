package com.webstartrek.wss.dao;

import com.webstartrek.wss.mapper.student.util.SudentTestUtil;
import com.webstartrek.wss.models.entities.student.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class GenericDAOTest {

    @Mock
    private EntityManagerProvider entityManagerProvider;

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Student> typedQuery; // Assuming a Student entity

    private DAOService<Student, String> genericDAO;

    private Student student;

    private static final String STUDENT_ID = "KKK-111-222-333";

    @BeforeEach
    void setUp() {
        openMocks(this);
        when(entityManagerProvider.getEntityManager()).thenReturn(entityManager);

        genericDAO = new GenericDAO<>(entityManagerProvider, Student.class);

        student = SudentTestUtil.getStudent();
        student.setId(1L);
        student.setStudentId(STUDENT_ID);
    }

    // Test for create method
    @Test
    public void testCreateSuccess() {
        genericDAO.create(student);
        verify(entityManager, times(1)).persist(student);
    }

    @Test
    public void testCreateFailure_RollbackException() {
        doThrow(new RollbackException("Transaction failed")).when(entityManager).persist(student);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> genericDAO.create(student));
        assertEquals("Failed to create entity", exception.getMessage());
    }

    // Test for read method
    @Test
    public void testReadSuccess() {
        when(entityManager.find(Student.class, STUDENT_ID)).thenReturn(student);
        System.out.println(student);
        Student result = genericDAO.read(STUDENT_ID);
        assertEquals(student, result);
        verify(entityManager, times(1)).find(Student.class, STUDENT_ID);
    }

    @Test
    public void testReadFailure_EntityNotFound() {
        when(entityManager.find(Student.class, STUDENT_ID)).thenReturn(null);
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> genericDAO.read(STUDENT_ID));
        assertEquals("Student not found: " + STUDENT_ID, exception.getMessage());
    }

    // Test for readAll method
    @Test
    public void testReadAllSuccess() {
        when(entityManager.createQuery(anyString(), eq(Student.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Collections.singletonList(student));

        List<Student> result = genericDAO.readAll(1, 10);
        assertEquals(1, result.size());
        verify(typedQuery).setFirstResult(0);
        verify(typedQuery).setMaxResults(10);
    }

    @Test
    public void testReadAllFailure_InvalidPagination() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> genericDAO.readAll(0, 10));
        assertEquals("Page number and size must be greater than 0", exception.getMessage());
    }

    // Test for update method
    @Test
    public void testUpdateSuccess() {
        genericDAO.update(student);
        verify(entityManager, times(1)).merge(student);
    }

    @Test
    public void testUpdateFailure_RollbackException() {
        doThrow(new RollbackException("Transaction failed")).when(entityManager).merge(student);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> genericDAO.update(student));
        assertEquals("Failed to update entity", exception.getMessage());
    }

    // Test for delete method
    @Test
    public void testDeleteSuccess() {
        when(entityManager.find(Student.class, STUDENT_ID)).thenReturn(student);
        genericDAO.delete(STUDENT_ID);
        verify(entityManager, times(1)).remove(student);
    }

    @Test
    public void testDeleteFailure_RollbackException() {
        when(entityManager.find(Student.class, STUDENT_ID)).thenReturn(student);
        doThrow(new RollbackException("Transaction failed")).when(entityManager).remove(student);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> genericDAO.delete(STUDENT_ID));
        assertEquals("Failed to delete entity", exception.getMessage());
    }

    @Test
    public void testDeleteNonExistentEntity() {
        String stdId = STUDENT_ID+"EXTRA";
        when(entityManager.find(Student.class, stdId)).thenReturn(null);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> genericDAO.delete(stdId));
        verify(entityManager, never()).remove(any());
        assertEquals("Student not found: "+ stdId, exception.getMessage());
    }
}
