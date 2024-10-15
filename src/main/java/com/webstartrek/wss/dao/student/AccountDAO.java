package com.webstartrek.wss.dao.student;

import com.webstartrek.wss.annotations.AccountDAOIdentifier;
import com.webstartrek.wss.dao.DAOService;
import com.webstartrek.wss.dao.EntityManagerProvider;
import com.webstartrek.wss.dao.GenericDAO;
import com.webstartrek.wss.models.entities.student.StudentAccount;

import javax.ejb.Stateless;
import javax.inject.Inject;

@AccountDAOIdentifier
@Stateless
public class AccountDAO extends GenericDAO<StudentAccount, String> implements DAOService <StudentAccount, String> {

    @Inject
    public AccountDAO(EntityManagerProvider entityManagerProvider, Class<StudentAccount> entityClass) {
        super(entityManagerProvider, entityClass);
    }
}
