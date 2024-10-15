package com.webstartrek.wss.dao;

import lombok.Getter;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Getter
@ApplicationScoped
public class EntityManagerProvider {

    @PersistenceContext(unitName = "wssPU")
    private EntityManager entityManager;

}
