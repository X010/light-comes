package com.light.outside.comes.mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Created by jeffrey on 11/10/15.
 */
@Configuration
@EnableTransactionManagement
public class DataBaseConfiguration implements EnvironmentAware {

    private RelaxedPropertyResolver propertyResolver;

    private static Logger LOG = LoggerFactory.getLogger(DataBaseConfiguration.class);

    @Primary
    @Bean(name = "dataSource", destroyMethod = "close")
    public DataSource dataSource() {
        LOG.debug("Configruing Write DataSource");
        DruidDataSource datasource = new DruidDataSource();
        try {
            datasource.setUrl(propertyResolver.getProperty("dburl"));
            datasource.setDriverClassName(propertyResolver.getProperty("driverClass"));
            datasource.setUsername(propertyResolver.getProperty("username"));
            datasource.setPassword(propertyResolver.getProperty("password"));
            datasource.setMaxActive(5);
            datasource.setInitialSize(1);
            datasource.setMaxActive(60000);
            datasource.setMinIdle(10);
            datasource.setTimeBetweenEvictionRunsMillis(60000);
            datasource.setMinEvictableIdleTimeMillis(30000);
            datasource.setValidationQuery("SELECT 'x'");
            datasource.setTestWhileIdle(true);
            datasource.setTestOnBorrow(false);
            datasource.setTestOnReturn(false);
            datasource.setMaxOpenPreparedStatements(20);
            datasource.setRemoveAbandoned(false);
            datasource.setRemoveAbandonedTimeout(1800);
            datasource.setLogAbandoned(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datasource;
    }


    @Override
    public void setEnvironment(Environment environment) {
        this.propertyResolver = new RelaxedPropertyResolver(environment, "jdbc.");
    }
}
