package com.webstartrek.wss.dao;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface DAOService <T, ID> {
    void create(@NotNull @Valid T entity);

    T read(@NotNull ID id);

    List<T> readAll(int pageNumber, int pageSize);

    @Transactional
    void update(@NotNull @Valid T entity);

    @Transactional
    void delete(@NotNull ID id);

    <S> long countAll(Class<S> entityClass);

    List<T> searchByColumn(String searchField, String columnName);

    List<T> searchByColumn(String searchField);
}
