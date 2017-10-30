package com.wil;

import com.wil.dao.UserDao;
import com.wil.service.UserService;
import com.wil.service.impl.UserServiceImpl;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by wil on 2017/10/30.
 */
@Configuration//替代基础xml
@ComponentScan//DI
@EnableAspectJAutoProxy//AOP
@PropertySource(value = "classpath:config.properties",ignoreResourceNotFound = true)
public class Application {

    /*@Value("${jdbc.driver}")
    private String driverClassName;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String userName;
    @Value("${jdbc.password}")
    private String password;*/

    @Autowired
    private Environment environment;

    @Bean
    public DataSource datasource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(environment.getProperty("jdbc.driver"));
        basicDataSource.setUrl(environment.getProperty("jdbc.url"));
        basicDataSource.setUsername(environment.getProperty("jdbc.username"));
        basicDataSource.setPassword(environment.getProperty("jdbc.password"));
        return basicDataSource;

    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(datasource());
        return jdbcTemplate;
    }

    @Bean
    public UserService userService() {
       UserServiceImpl userService = new UserServiceImpl();
       userService.setUserDao(new UserDao());
       return userService;

    }

}
