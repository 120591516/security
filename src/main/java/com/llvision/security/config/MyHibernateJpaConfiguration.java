package com.llvision.security.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;

/**
 * Created by llvision on 17/5/30.
 */
public class MyHibernateJpaConfiguration extends HibernateJpaAutoConfiguration {

    private final String[] packagesToScan;

    public MyHibernateJpaConfiguration(DataSource dataSource,
                                       JpaProperties jpaProperties,
                                       ObjectProvider<JtaTransactionManager> jtaTransactionManager,
                                       ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers,
                                       String... packagesToScan) {
        super(dataSource, jpaProperties, jtaTransactionManager, transactionManagerCustomizers);

        this.packagesToScan = packagesToScan;
    }

    @Override
    protected String[] getPackagesToScan() {
        return packagesToScan;
    }

}
