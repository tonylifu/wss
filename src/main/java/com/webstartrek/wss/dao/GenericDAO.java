package com.webstartrek.wss.dao;

import javax.ejb.Stateless;
import javax.inject.Inject;
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
public class GenericDAO<T, ID> implements DAOService <T, ID> {

    private final EntityManagerProvider entityManagerProvider;
    private final Class<T> entityClass;

    @Inject
    public GenericDAO(EntityManagerProvider entityManagerProvider, Class<T> entityClass) {
        this.entityManagerProvider = entityManagerProvider;
        this.entityClass = entityClass;
    }

    private static final Logger LOGGER = Logger.getLogger(GenericDAO.class.getName());

    @Transactional
    @Override
    public void create(@NotNull @Valid T entity) {
        try {
            entityManagerProvider.getEntityManager().persist(entity);
            LOGGER.info("Successfully created entity: " + entity);
        } catch (RollbackException e) {
            LOGGER.log(Level.SEVERE, "Error creating entity: " + entity, e);
            throw new RuntimeException("Failed to create entity");
        }
    }

    @Override
    public T read(@NotNull ID id) {
        T entity = entityManagerProvider.getEntityManager().find(entityClass, id);
        if (entity == null) {
            LOGGER.warning(entityClass.getSimpleName() + " not found: " + id);
            throw new EntityNotFoundException(entityClass.getSimpleName() + " not found: " + id);
        }
        LOGGER.info("Successfully retrieved entity: " + entity);
        return entity;
    }

    @Override
    public List<T> readAll(int pageNumber, int pageSize) {
        // Input validation for pagination
        if (pageNumber < 1 || pageSize < 1) {
            throw new IllegalArgumentException("Page number and size must be greater than 0");
        }

        String entityName = entityClass.getSimpleName();
        TypedQuery<T> query = entityManagerProvider.getEntityManager().createQuery("SELECT e FROM " + entityName + " e", entityClass);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        List<T> entities = query.getResultList();
        LOGGER.info("Successfully retrieved " + entities.size() + " entities from page " + pageNumber);
        return entities;
    }

    @Transactional
    @Override
    public void update(@NotNull @Valid T entity) {
        try {
            entityManagerProvider.getEntityManager().merge(entity);
            LOGGER.info("Successfully updated entity: " + entity);
        } catch (RollbackException e) {
            LOGGER.log(Level.SEVERE, "Error updating entity: " + entity, e);
            throw new RuntimeException("Failed to update entity");
        }
    }

    @Transactional
    @Override
    public void delete(@NotNull ID id) {
        T entity = read(id); // Use read method to check existence and fetch
        if (entity != null) {
            try {
                entityManagerProvider.getEntityManager().remove(entity);
                LOGGER.info("Successfully deleted entity: " + id);
            } catch (RollbackException e) {
                LOGGER.log(Level.SEVERE, "Error deleting entity: " + id, e);
                throw new RuntimeException("Failed to delete entity");
            }
        } else {
            LOGGER.warning("Attempted to delete non-existent entity: " + id);
        }
    }

    @Override
    public <S> long countAll(Class<S> entityClass) {
        String query = String.format("SELECT COUNT(e) FROM %s e", entityClass.getSimpleName());
        return entityManagerProvider.getEntityManager().createQuery(query, Long.class).getSingleResult();
    }

}
