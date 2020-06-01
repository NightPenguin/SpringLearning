package com.springboot.multidatasource.config;

import com.alibaba.druid.pool.DruidDataSource;
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


@Configuration
@MapperScan(basePackages = MasterDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "masterSqlSessionFactory")
public class MasterDataSourceConfig {
    // mapper 路径
    public static final String PACKAGE = "com.springboot.multidatasource.mapper.master";
    // mapper.xml 路径
    public static final String MAPPER_LOCATION = "classpath:mapper/master/*.xml";
    //mybatis 配置路径
    public static  final String MABATIS_LOCATION = "classpath:mapper/mybatis-config.xml";


    @Bean("masterDatasource")
    @Primary  // 默认数据源
    @ConfigurationProperties(prefix = "spring.datasource.druid.master", ignoreInvalidFields = false)
    public DataSource masterDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean("masterTransactionManager")
    @Primary
    public DataSourceTransactionManager masterDataSourceTransactionManager(){
        return new DataSourceTransactionManager(masterDataSource());
    }

    @Bean("masterSqlSessionFactory")
    @Primary
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("masterDatasource") DataSource dataSource) throws Exception{
        final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        // 不使用注解，使用xml
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MasterDataSourceConfig.MAPPER_LOCATION));
        sqlSessionFactoryBean.setConfigLocation(new DefaultResourceLoader().getResource(MasterDataSourceConfig.MABATIS_LOCATION));
        return sqlSessionFactoryBean.getObject();
    }
}
