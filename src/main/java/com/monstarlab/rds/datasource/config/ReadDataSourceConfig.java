package com.monstarlab.rds.datasource.config;

import com.monstarlab.rds.datasource.delegate.ReadOnlyRepository;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;


@Slf4j
@Configuration
@EnableJpaRepositories(
        basePackages = "com.monstarlab.rds.datasource.repository",
        includeFilters = @ComponentScan.Filter(ReadOnlyRepository.class),
        entityManagerFactoryRef = "readEntityManagerFactory",
        considerNestedRepositories = true
)
public class ReadDataSourceConfig {

    @Value("${spring.datasource.hikari.maximum-pool-size:20}")
    private Integer maxPoolSize;

    @Value("${spring.datasource.hikari.minimum-idle:10}")
    private Integer idlePoolSize;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.read")
    public DataSourceProperties readDataSourceProperties() {
        log.info("Creating Read Datasource Configs");
        return new DataSourceProperties();
    }

    @Bean
    public HikariDataSource readDataSource(DataSourceProperties readDataSourceProperties) {
        log.info("Creating Read Datasource: URL: {}, User: {}", readDataSourceProperties.getUrl(), readDataSourceProperties.getUsername());
        HikariDataSource hikariDataSource = readDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        hikariDataSource.setMaximumPoolSize(maxPoolSize);
        hikariDataSource.setMinimumIdle(idlePoolSize);
        hikariDataSource.setReadOnly(true);//This will make sure the Transactions will remain read-only for this Entity Manager.
        return hikariDataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean readEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("readDataSource") DataSource dataSource) {

        return builder
                .dataSource(dataSource)
                .packages("com.monstarlab.rds.datasource.schema")
                .persistenceUnit("read")
                .build();
    }
}
