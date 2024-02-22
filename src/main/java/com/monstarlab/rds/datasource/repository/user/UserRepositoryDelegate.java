package com.monstarlab.rds.datasource.repository.user;

import com.monstarlab.rds.datasource.delegate.JpaRepositoryDelegate;
import com.monstarlab.rds.datasource.delegate.PrimaryRepository;
import com.monstarlab.rds.datasource.delegate.ReadOnlyRepository;
import com.monstarlab.rds.datasource.schema.User;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Primary
@Component
@RequiredArgsConstructor
class UserRepositoryDelegate  extends JpaRepositoryDelegate<User, Long> implements UserRepository {

    private final PrimaryUserRepository primaryUserRepository;
    private final ReadOnlyUserRepository readOnlyUserRepository;

    @Override
    public Boolean existsByUuid(UUID uuid) {
        return getReadOnlyRepository().existsByUuid(uuid);
    }

    @Override
    public User findByUuid(UUID uuid) {
        return getReadOnlyRepository().findByUuid(uuid);
    }

    @Override
    public void delete(@NotNull User entity) {
        getPrimaryRepository().delete(entity);
    }

    @Override
    public void deleteAll() {
        getReadOnlyRepository().deleteAll();
    }

    @PrimaryRepository
    interface PrimaryUserRepository extends UserRepository { }

    @ReadOnlyRepository
    @PersistenceContext(name = "read")
    interface ReadOnlyUserRepository extends UserRepository { }

    @Override
    protected PrimaryUserRepository getPrimaryRepository() {
        return primaryUserRepository;
    }

    @Override
    protected ReadOnlyUserRepository getReadOnlyRepository() {
        return readOnlyUserRepository;
    }
}