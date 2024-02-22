package com.monstarlab.rds.datasource.delegate;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@SuppressWarnings("java:S119")
public abstract class JpaRepositoryDelegate<T, ID> implements JpaRepository<T, ID> {

    protected abstract JpaRepository<T, ID> getPrimaryRepository();

    protected abstract JpaRepository<T, ID> getReadOnlyRepository();

    @NotNull
    @Override
    public List<T> findAll() {
        return getReadOnlyRepository().findAll();
    }

    @NotNull
    @Override
    public List<T> findAll(@NotNull Sort sort) {
        return getReadOnlyRepository().findAll(sort);
    }

    @NotNull
    @Override
    public Page<T> findAll(@NotNull Pageable pageable) {
        return getReadOnlyRepository().findAll(pageable);
    }

    @NotNull
    @Override
    public List<T> findAllById(@NotNull Iterable<ID> ids) {
        return getReadOnlyRepository().findAllById(ids);
    }

    @NotNull
    @Override
    public T getReferenceById(@NotNull ID id) {
        return getReadOnlyRepository().getReferenceById(id);
    }

    @Override
    public long count() {
        return getReadOnlyRepository().count();
    }

    @Override
    public void deleteById(@NotNull ID id) {
        getPrimaryRepository().deleteById(id);
    }

    @Override
    public void delete(@NotNull T entity) {
        getPrimaryRepository().delete(entity);
    }

    @Override
    public void deleteAllById(@NotNull Iterable<? extends ID> ids) {
        getPrimaryRepository().deleteAllById(ids);
    }

    @Override
    public void deleteAll(@NotNull Iterable<? extends T> entities) {
        getPrimaryRepository().deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        getPrimaryRepository().deleteAll();
    }

    @NotNull
    @Override
    public <S extends T> S save(@NotNull S entity) {
        return getPrimaryRepository().save(entity);
    }

    @NotNull
    @Override
    public <S extends T> List<S> saveAll(@NotNull Iterable<S> entities) {
        return getPrimaryRepository().saveAll(entities);
    }

    @NotNull
    @Override
    public Optional<T> findById(@NotNull ID id) {
        return getReadOnlyRepository().findById(id);
    }

    @Override
    public boolean existsById(@NotNull ID id) {
        return getReadOnlyRepository().existsById(id);
    }

    @Override
    public void flush() {
        getPrimaryRepository().flush();
    }

    @NotNull
    @Override
    public <S extends T> S saveAndFlush(@NotNull S entity) {
        return getPrimaryRepository().saveAndFlush(entity);
    }

    @NotNull
    @Override
    public <S extends T> List<S> saveAllAndFlush(@NotNull Iterable<S> entities) {
        return getPrimaryRepository().saveAllAndFlush(entities);
    }

    @Override
    public void deleteAllInBatch(@NotNull Iterable<T> entities) {
        getPrimaryRepository().deleteAllInBatch(entities);
    }

    @Override
    public void deleteAllByIdInBatch(@NotNull Iterable<ID> ids) {
        getPrimaryRepository().deleteAllByIdInBatch(ids);
    }

    @Override
    public void deleteAllInBatch() {
        getPrimaryRepository().deleteAllInBatch();
    }

    @NotNull
    @Override
    public T getOne(@NotNull ID id) {
        throw new UnsupportedOperationException("Spring Data has deprecated this method, so it won't be implemented. Use findById() instead.");
    }

    @NotNull
    @Override
    public T getById(@NotNull ID id) {
        return getReadOnlyRepository().getById(id);
    }

    @NotNull
    @Override
    public <S extends T> Optional<S> findOne(@NotNull Example<S> example) {
        return getReadOnlyRepository().findOne(example);
    }

    @NotNull
    @Override
    public <S extends T> List<S> findAll(@NotNull Example<S> example) {
        return getReadOnlyRepository().findAll(example);
    }

    @NotNull
    @Override
    public <S extends T> List<S> findAll(@NotNull Example<S> example, @NotNull Sort sort) {
        return getReadOnlyRepository().findAll(example, sort);
    }

    @NotNull
    @Override
    public <S extends T> Page<S> findAll(@NotNull Example<S> example, @NotNull Pageable pageable) {
        return getReadOnlyRepository().findAll(example, pageable);
    }

    @Override
    public <S extends T> long count(@NotNull Example<S> example) {
        return getReadOnlyRepository().count(example);
    }

    @Override
    public <S extends T> boolean exists(@NotNull Example<S> example) {
        return getReadOnlyRepository().exists(example);
    }

    @NotNull
    @Override
    public <S extends T, R> R findBy(@NotNull Example<S> example, @NotNull Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return getReadOnlyRepository().findBy(example, queryFunction);
    }

}