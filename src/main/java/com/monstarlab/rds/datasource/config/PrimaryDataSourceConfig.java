package com.monstarlab.rds.datasource.config;

import com.monstarlab.rds.datasource.delegate.PrimaryRepository;
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
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;


@Slf4j
@Configuration
@EnableJpaRepositories(
        basePackages = "com.monstarlab.rds.datasource.repository",
        includeFilters = @ComponentScan.Filter(PrimaryRepository.class),
        entityManagerFactoryRef = "primaryEntityManagerFactory",
        considerNestedRepositories = true
)
public class PrimaryDataSourceConfig {

    @Value("${spring.datasource.hikari.maximum-pool-size:20}")
    private Integer maxPoolSize;

    @Value("${spring.datasource.hikari.minimum-idle:10}")
    private Integer idlePoolSize;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSourceProperties primaryDataSourceProperties() {
        log.info("Creating Primary Datasource Configs");
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public HikariDataSource primaryDatasource(DataSourceProperties primaryDataSourceProperties) {
        log.info("Creating Primary Datasource: URL: {}, User: {}", primaryDataSourceProperties.getUrl(), primaryDataSourceProperties.getUsername());
        HikariDataSource hikariDataSource = primaryDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        hikariDataSource.setMaximumPoolSize(maxPoolSize);
        hikariDataSource.setMinimumIdle(idlePoolSize);
        return hikariDataSource;
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("primaryDatasource") DataSource dataSource) {

        return builder.dataSource(dataSource)
                .packages("com.monstarlab.rds.datasource.schema")
                .persistenceUnit("primary")
                .build();
    }
}
