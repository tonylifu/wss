package com.webstartrek.wss.dao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.Collections;
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
        TypedQuery<T> query = entityManagerProvider.getEntityManager().createQuery("SELECT e FROM " +
                entityName + " e ORDER BY e.createdAt DESC", entityClass);
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

    @Override
    public List<T> searchByColumn(String searchField, String columnName) {
        if (searchField == null || searchField.trim().isEmpty()) {
            LOGGER.log(Level.WARNING, "Search field is empty or null");
            return Collections.emptyList(); // Return empty list for invalid input
        }

        if (columnName == null || columnName.trim().isEmpty()) {
            LOGGER.log(Level.WARNING, "Column name is empty or null");
            return Collections.emptyList(); // Return empty list for invalid column
        }

        try {
            CriteriaBuilder cb = entityManagerProvider.getEntityManager().getCriteriaBuilder();
            CriteriaQuery<T> query = cb.createQuery(entityClass);
            Root<T> root = query.from(entityClass);

            // Build the 'ILIKE' equivalent with LOWER and LIKE for case-insensitive search
            Predicate predicate = cb.like(cb.lower(root.get(columnName)), "%" + searchField.toLowerCase() + "%");
            query.select(root).where(predicate);

            // Execute query
            List<T> resultList = entityManagerProvider.getEntityManager().createQuery(query).getResultList();

            // Logging the number of results
            LOGGER.log(Level.INFO, "Query returned {0} results", resultList.size());

            return resultList;

        } catch (IllegalArgumentException e) {
            // Log column name doesn't exist or other criteria issues
            LOGGER.log(Level.SEVERE, "Invalid column name: " + columnName, e);
            return Collections.emptyList();

        } catch (Exception e) {
            // Catch all other exceptions, ensuring resilience
            LOGGER.log(Level.SEVERE, "An error occurred during the search operation", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<T> searchByColumn(String searchField) {
        // Validate input
        if (searchField == null || searchField.trim().isEmpty()) {
            LOGGER.log(Level.WARNING, "Search field is null or empty");
            return Collections.emptyList(); // Return empty list for invalid input
        }

        CriteriaBuilder cb = entityManagerProvider.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(entityClass);
        Root<T> root = query.from(entityClass);

        // Build a dynamic list of predicates based on string fields
        Predicate combinedPredicate = cb.disjunction(); // Start with disjunction (false)

        // Use reflection to dynamically create predicates for string fields
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.getType().equals(String.class)) {
                try {
                    // Create a predicate for the current string field
                    Predicate predicate = cb.like(cb.lower(root.get(field.getName())), "%" + searchField.toLowerCase() + "%");
                    combinedPredicate = cb.or(combinedPredicate, predicate); // Combine with OR
                } catch (IllegalArgumentException e) {
                    LOGGER.log(Level.WARNING, "Field not found: " + field.getName(), e);
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, "An unexpected error occurred while processing field: " + field.getName(), e);
                }
            }
        }

        query.select(root).where(combinedPredicate);

        try {
            // Execute query and log the result size
            List<T> resultList = entityManagerProvider.getEntityManager().createQuery(query).getResultList();
            LOGGER.log(Level.INFO, "Query returned {0} results", resultList.size());
            return resultList;
        } catch (Exception e) {
            // Handle unexpected errors during query execution
            LOGGER.log(Level.SEVERE, "An error occurred during the search operation", e);
            return Collections.emptyList();
        }
    }
}
