package com.monstarlab.rds.datasource.delegate;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("java:S119")
public abstract class SpecificationRepositoryDelegate<T, ID> extends JpaRepositoryDelegate<T, ID> implements SpecificationRepository<T, ID> {

    protected abstract SpecificationRepository<T, ID> getPrimaryRepository();

    protected abstract SpecificationRepository<T, ID> getReadOnlyRepository();

    @NotNull
    @Override
    public Optional<T> findOne(Specification<T> spec) {
        return getReadOnlyRepository().findOne(spec);
    }

    @NotNull
    @Override
    public List<T> findAll(Specification<T> spec) {
        return getReadOnlyRepository().findAll(spec);
    }

    @NotNull
    @Override
    public Page<T> findAll(Specification<T> spec,  @NotNull Pageable pageable) {
        return getReadOnlyRepository().findAll(spec, pageable);
    }

    @NotNull
    @Override
    public List<T> findAll(Specification<T> spec, @NotNull Sort sort) {
        return getReadOnlyRepository().findAll(spec, sort);
    }

    @Override
    public long count(Specification<T> spec) {
        return getReadOnlyRepository().count(spec);
    }

}