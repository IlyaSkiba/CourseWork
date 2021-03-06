package com.bsu.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

/**
 * @author HomeUser
 *         Date: 13.1.13
 *         Time: 18.51
 */
@Configuration
@ComponentScan(basePackages = {"com.bsu.server"})
@PropertySource("classpath:jdbc.properties")
@EnableTransactionManagement
public class ServerConfiguration {

    @Autowired
    private Environment environment;

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return jpaTransactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setDataSource(dataSource());
        bean.setPersistenceUnitName("JGeekPU");
        bean.setJpaVendorAdapter(new HibernateJpaVendorAdapter() {
            {
                setDatabasePlatform(environment.getProperty("hibernate.dialect"));
                setGenerateDdl(true);
                setShowSql(Boolean.parseBoolean(environment.getProperty("hibernate.format_sql")));
            }
        });
        bean.setJpaProperties(new Properties() {
            {
                setProperty("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));
            }
        });
        bean.setPackagesToScan("com.bsu.server");
        return bean;
    }

    @Bean
    public BasicDataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(environment.getProperty("jdbc.url"));
        dataSource.setUsername(environment.getProperty("jdbc.username"));
        dataSource.setPassword(environment.getProperty("jdbc.password"));
        dataSource.setMaxActive(1000);
        dataSource.setMaxWait(1000);
        dataSource.setDefaultReadOnly(false);
        dataSource.setDefaultCatalog(environment.getProperty("hibernate.default_schema"));
        dataSource.setPoolPreparedStatements(true);
        dataSource.setDefaultAutoCommit(true);
        return dataSource;
    }
}
