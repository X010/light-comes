package com.light.outside.comes.qbkl;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.inject.Inject;
import javax.sql.DataSource;

/**
 * Created by jeffrey on 11/10/15.
 */
@Configuration
@MapperScan(basePackages = {"com.light.outside.comes.qbkl.dao"},sqlSessionFactoryRef = "qblk")
public class QblkMybatisConfiguration implements EnvironmentAware {
    private Logger LOG = LoggerFactory.getLogger(QblkMybatisConfiguration.class);

    private RelaxedPropertyResolver propertyResolver;


    @Inject
    @Qualifier("qblkDataSource")
    private DataSource dataSource;


    @Bean(name="qblk")
    public SqlSessionFactory sqlSessionFactory() {
        try {
            SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
            sessionFactory.setDataSource(dataSource);
            sessionFactory.setTypeAliasesPackage(propertyResolver.getProperty("typeAliasesPackage"));
            sessionFactory
                    .setMapperLocations(new PathMatchingResourcePatternResolver()
                            .getResources(propertyResolver.getProperty("mapperLocations")));
            sessionFactory
                    .setConfigLocation(new DefaultResourceLoader()
                            .getResource(propertyResolver.getProperty("configLocation")));

            return sessionFactory.getObject();
        } catch (Exception e) {
            LOG.warn("Could not confiure mybatis session factory");
            return null;
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.propertyResolver = new RelaxedPropertyResolver(environment, "mybatisqblk.");
    }
}
