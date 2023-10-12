package com.example.socket.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan({"com.example.socket.repository"})
public class DataSourceConfig {

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String password;
/*
    @Value("${spring.datasource.hikari.connection-timeout}")
    private Long timeOut;

    @Value("${spring.datasource.hikari.maximum-pool-size}")
    private Integer idle;

    @Value("${spring.datasource.hikari.idle-timeout}")
    private Integer poolSize;*/

    @Bean
    public DataSource masterDataSource() {
        HikariConfig config = new HikariConfig();
        /*config.setConnectionTimeout(timeOut);
        config.setMaximumPoolSize(poolSize);
        config.setMinimumIdle(idle);*/
        config.setDriverClassName(driverClassName);
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(userName);
        config.setPassword(password);
        return new HikariDataSource(config);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath*:**/*Mapper.xml"));
        sqlSessionFactoryBean.setDataSource(masterDataSource());
        sqlSessionFactoryBean.setTypeAliasesPackage("com.example.socket.model.entity");
        return sqlSessionFactoryBean.getObject();
    }

}
