package com.webstartrek.wss.dao.student;

import com.webstartrek.wss.annotations.StudentDAOIdentifier;
import com.webstartrek.wss.dao.DAOService;
import com.webstartrek.wss.dao.EntityManagerProvider;
import com.webstartrek.wss.dao.GenericDAO;
import com.webstartrek.wss.models.entities.student.Student;

import javax.ejb.Stateless;
import javax.inject.Inject;

@StudentDAOIdentifier
@Stateless
public class StudentDAO extends GenericDAO<Student, String> implements DAOService <Student, String> {

    @Inject
    public StudentDAO(EntityManagerProvider entityManagerProvider, Class<Student> entityClass) {
        super(entityManagerProvider, entityClass);
    }
}
