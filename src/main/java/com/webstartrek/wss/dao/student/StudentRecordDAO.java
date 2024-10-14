package com.webstartrek.wss.dao.student;

import com.webstartrek.wss.models.entities.student.Student;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityNotFoundException;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class StudentRecordDAO implements StudentDAO {

    private static final Logger LOGGER = Logger.getLogger(StudentRecordDAO.class.getName());

    @PersistenceContext(unitName = "wssPU")
    private EntityManager entityManager;

    @Override
    @Transactional
    public void create(@NotNull @Valid Student student) {
        try {
            entityManager.persist(student);
            LOGGER.info("Successfully created student: " + student);
        } catch (RollbackException e) {
            LOGGER.log(Level.SEVERE, "Error creating student: " + student, e);
            throw new RuntimeException("Failed to create student");
        }
    }

    @Override
    public Student read(@NotNull String studentId) {
        Student student = entityManager.find(Student.class, studentId);
        if (student == null) {
            LOGGER.warning("Student not found: " + studentId);
            throw new EntityNotFoundException("Student not found: " + studentId);
        }
        LOGGER.info("Successfully retrieved student: " + student);
        return student;
    }

    @Override
    public List<Student> readAll(int pageNumber, int pageSize) {
        // Input validation for pagination
        if (pageNumber < 1 || pageSize < 1) {
            throw new IllegalArgumentException("Page number and size must be greater than 0");
        }

        TypedQuery<Student> query = entityManager.createQuery("SELECT s FROM Student s", Student.class);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        List<Student> students = query.getResultList();
        LOGGER.info("Successfully retrieved " + students.size() + " students from page " + pageNumber);
        return students;
    }

    @Override
    @Transactional
    public void update(@NotNull @Valid Student student) {
        try {
            entityManager.merge(student);
            LOGGER.info("Successfully updated student: " + student);
        } catch (RollbackException e) {
            LOGGER.log(Level.SEVERE, "Error updating student: " + student, e);
            throw new RuntimeException("Failed to update student");
        }
    }

    @Override
    @Transactional
    public void delete(@NotNull String studentId) {
        Student student = read(studentId); // Use read method to check existence and fetch
        if (student != null) {
            try {
                entityManager.remove(student);
                LOGGER.info("Successfully deleted student: " + studentId);
            } catch (RollbackException e) {
                LOGGER.log(Level.SEVERE, "Error deleting student: " + studentId, e);
                throw new RuntimeException("Failed to delete student");
            }
        } else {
            LOGGER.warning("Attempted to delete non-existent student: " + studentId);
        }
    }
}
