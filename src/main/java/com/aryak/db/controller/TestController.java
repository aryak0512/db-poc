package com.aryak.db.controller;

import com.aryak.db.beans.DatasourcesConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.jdbc.metadata.HikariDataSourcePoolMetadata;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.Map;

@RestController
public class TestController {

    private final DatasourcesConfig datasourcesConfig;

    public TestController(DatasourcesConfig datasourcesConfig) {
        this.datasourcesConfig = datasourcesConfig;
    }

    @GetMapping("/db")
    public Map<String, Integer> getHikariMetaData() {
        HikariDataSource dataSource = (HikariDataSource) datasourcesConfig.hikariDataSource();
        var hikariDataSourcePoolMetadata = new HikariDataSourcePoolMetadata(dataSource);
        Integer active = hikariDataSourcePoolMetadata.getActive();
        Integer max = hikariDataSourcePoolMetadata.getMax();
        Integer idle = hikariDataSourcePoolMetadata.getIdle();
        return Map.of("active", active, "max", max, "idle", idle);
    }
}
