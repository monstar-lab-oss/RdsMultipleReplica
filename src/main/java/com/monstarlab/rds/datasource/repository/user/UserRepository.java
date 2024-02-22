package com.monstarlab.rds.datasource.repository.user;

import com.monstarlab.rds.datasource.schema.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

@NoRepositoryBean
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByUuid(UUID uuid);

    User findByUuid(UUID uuid);

}