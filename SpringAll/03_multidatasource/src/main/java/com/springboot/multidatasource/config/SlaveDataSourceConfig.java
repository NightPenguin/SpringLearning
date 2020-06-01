package com.springboot.multidatasource.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@MapperScan(basePackages = SlaveDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "slaveSqlSessionFactory")
@Configuration
public class SlaveDataSourceConfig {
    // mapper 路径
    public static final String PACKAGE = "com.springboot.multidatasource.mapper.slave";
    // mapper.xml 路径
    static final String MAPPER_LOCATION = "classpath:mapper/slave/*.xml";
    public static  final String MABATIS_LOCATION = "classpath:mapper/mybatis-config.xml";

    @Bean("slaveDatasource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.slave", ignoreInvalidFields = false)
    public DataSource slaveDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean("slaveTransactionManager")
    public DataSourceTransactionManager slaveDataSourceTransactionManager(){
        return new DataSourceTransactionManager(slaveDataSource());
    }

    @Bean("slaveSqlSessionFactory")
    public SqlSessionFactory slaveSqlSessionFactory(@Qualifier("slaveDatasource") DataSource dataSource) throws Exception{
        final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        // 不使用注解，使用xml
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(SlaveDataSourceConfig.MAPPER_LOCATION));
        sqlSessionFactoryBean.setConfigLocation(new DefaultResourceLoader().getResource(SlaveDataSourceConfig.MABATIS_LOCATION));
        return sqlSessionFactoryBean.getObject();
    }
}
