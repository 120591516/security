package com.llvision.security.config;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.llvision.security.domain.User;
import io.github.jhipster.config.JHipsterConstants;
import io.github.jhipster.config.liquibase.AsyncSpringLiquibase;
import liquibase.integration.spring.SpringLiquibase;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
// @EnableJpaRepositories("com.llvision.security.repository")
@EnableJpaRepositories(
    basePackages = "com.llvision.security.repository",
    entityManagerFactoryRef = "primaryEntityManagerFactory",
    transactionManagerRef = "primaryTransactionManager"
)
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
@EnableTransactionManagement
public class DatabaseConfiguration {

    private final Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);

    private final Environment env;

    public DatabaseConfiguration(Environment env) {
        this.env = env;
    }

    /**
     * Open the TCP port for the H2 database, so it is available remotely.
     *
     * @return the H2 database TCP server
     * @throws SQLException if the server failed to start
     */
    @Bean(initMethod = "start", destroyMethod = "stop")
    @Profile(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT)
    public Server h2TCPServer() throws SQLException {
        return Server.createTcpServer("-tcp","-tcpAllowOthers");
    }

    @Bean
    public SpringLiquibase liquibase(@Qualifier("taskExecutor") TaskExecutor taskExecutor,
            DataSource dataSource, LiquibaseProperties liquibaseProperties) {

        // Use liquibase.integration.spring.SpringLiquibase if you don't want Liquibase to start asynchronously
        SpringLiquibase liquibase = new AsyncSpringLiquibase(taskExecutor, env);
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:config/liquibase/master.xml");
        liquibase.setContexts(liquibaseProperties.getContexts());
        liquibase.setDefaultSchema(liquibaseProperties.getDefaultSchema());
        liquibase.setDropFirst(liquibaseProperties.isDropFirst());
        if (env.acceptsProfiles(JHipsterConstants.SPRING_PROFILE_NO_LIQUIBASE)) {
            liquibase.setShouldRun(false);
        } else {
            liquibase.setShouldRun(liquibaseProperties.isEnabled());
            log.debug("Configuring Liquibase");
        }
        return liquibase;
    }

    @Bean
    public Hibernate5Module hibernate5Module() {
        return new Hibernate5Module();
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties primaryDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSource primaryDataSource(DataSourceProperties primaryDataSourceProperties) {
        return primaryDataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    @ConfigurationProperties("spring.jpa")
    public JpaProperties primaryJpaProperties() {
        return new JpaProperties();
    }

    @Bean
    @ConfigurationProperties("spring.jpa")
    public MyHibernateJpaConfiguration primaryMyHibernateJpaConfiguration(
        DataSource primaryDataSource,
        JpaProperties primaryJpaProperties,
        ObjectProvider<JtaTransactionManager> jtaTransactionManager,
        ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {

        MyHibernateJpaConfiguration jpaConfiguration = new MyHibernateJpaConfiguration(
                primaryDataSource,
                primaryJpaProperties,
                jtaTransactionManager,
                transactionManagerCustomizers,
                User.class.getPackage().getName());
        return jpaConfiguration;
    }

    @Bean
    @ConfigurationProperties("spring.jpa")
    public JpaVendorAdapter primaryJpaVendorAdapter(
        MyHibernateJpaConfiguration primaryMyHibernateJpaConfiguration) {
        return primaryMyHibernateJpaConfiguration.jpaVendorAdapter();
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.jpa")
    public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(
        MyHibernateJpaConfiguration primaryMyHibernateJpaConfiguration,
        JpaVendorAdapter primaryJpaVendorAdapter,
        ObjectProvider<PersistenceUnitManager> persistenceUnitManager) {

        return primaryMyHibernateJpaConfiguration.entityManagerFactory(
            primaryMyHibernateJpaConfiguration.entityManagerFactoryBuilder(
                primaryJpaVendorAdapter, persistenceUnitManager));
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.jpa")
    public PlatformTransactionManager primaryTransactionManager(
        MyHibernateJpaConfiguration primaryMyHibernateJpaConfiguration) {
        return primaryMyHibernateJpaConfiguration.transactionManager();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource primaryDataSourceProperties){
        return new JdbcTemplate(primaryDataSourceProperties);
    }
}
