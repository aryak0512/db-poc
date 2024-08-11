package com.aryak.db.beans;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class DatasourcesConfig {

    public DataSource h2DataSource(){
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl("jdbc:h2:mem:testdb");
        ds.setPassword("");
        ds.setUsername("sa");
        ds.setMaxTotal(20);
        ds.setDriverClassName("org.h2.Driver");
        return ds;
    }

    public DataSource mySqlDataSource(){
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl("jdbc:mysql://localhost:3306/eshop");
        ds.setPassword("example");
        ds.setUsername("root");
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return ds;
    }

    /**
     * configure the connection pool properties
     * @return the datasource needed to instantiate JDBC template
     */
    //@Bean
    public DataSource hikariDataSource(){
        HikariConfig config = new HikariConfig();
        config.setMaximumPoolSize(2);
        config.setPoolName("aryaks-pool");
        config.setPassword("");
        config.setUsername("sa");
        config.setJdbcUrl("jdbc:h2:mem:testdb");
        config.setDriverClassName("org.h2.Driver");
        return new HikariDataSource(config);
    }

    //@Bean(name = "jdbcTemplate")
    @Profile(value = "local")
    public JdbcTemplate h2Template(){
        return new JdbcTemplate(hikariDataSource());
    }

    //@Bean(value = "jdbcTemplate")
    @Profile(value = "qa")
    public JdbcTemplate mysqlTemplate(){
        return new JdbcTemplate(mySqlDataSource());
    }
}
