package com.casualsource.casualcrawler.datawriter.DataWriterImpl.JdbcWriterEtc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class JdbcTemplateConfig {

    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public JdbcOperations jdbcOperations(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    public static void setDriver(String driver) {
        JdbcTemplateConfig.driver = driver;
    }

    public static void setUrl(String url) {
        JdbcTemplateConfig.url = url;
    }

    public static void setUsername(String username) {
        JdbcTemplateConfig.username = username;
    }

    public static void setPassword(String password) {
        JdbcTemplateConfig.password = password;
    }

}
