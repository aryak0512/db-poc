package com.aryak.db.multitenancy;

import com.aryak.db.domain.DatabaseType;
import com.aryak.db.domain.Tenant;
import com.aryak.db.utils.TenantUtils;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class MultitenancyConfiguration {

    private final TenantStore tenantStore;

    public MultitenancyConfiguration(TenantStore tenantStore) {
        this.tenantStore = tenantStore;
    }

    public void loadTenants() {

        // https://www.baeldung.com/multitenancy-with-spring-data-jpa

        getAllTenants().stream()
                .forEach(tenant -> {
                    tenantStore.put(tenant);
                    String url = TenantUtils.getUrl(tenant);

                    try {
                        var jdbcTemplate = createTemplate(tenant, url);
                        tenantStore.put(tenant, jdbcTemplate);
                    } catch (Exception e) {
                        log.error("Exception in connection for client : {} | Message : {}", tenant.getTenantId(), e.getMessage());
                    }

                });
    }

    /**
     * Obtains client information from DB or REST call
     *
     * @return list of all tenant configuration
     */
    private List<Tenant> getAllTenants() {

        Tenant tenant = Tenant.builder()
                .host("127.0.0.1")
                .password("example")
                .tenantId("client1")
                .databaseName("eshop")
                .dbType(DatabaseType.MYSQL)
                .username("root")
                .port(3306)
                .driver("com.mysql.cj.jdbc.Driver")
                .build();

        Tenant tenant2 = Tenant.builder()
                .host("127.0.0.1")
                .password("root")
                .tenantId("client2")
                .databaseName("eshop")
                .dbType(DatabaseType.POSTGRES)
                .username("postgres")
                .port(5433)
                .driver("org.postgresql.Driver")
                .build();

        // buggy config
        Tenant tenant3 = Tenant.builder()
                .host("127.0.0.1")
                .password("root")
                .tenantId("client3")
                .databaseName("eshop")
                .dbType(DatabaseType.POSTGRES)
                .username("postgres")
                .port(6379)
                .driver("org.postgresql.Driver")
                .build();

        Tenant tenant4 = Tenant.builder()
                .host("127.0.0.1")
                .password("root")
                .tenantId("client4")
                .databaseName("eshop")
                .dbType(DatabaseType.POSTGRES)
                .username("postgresfdef")
                .port(5433)
                .driver("org.postgresql.Driver")
                .build();

        return List.of(tenant2, tenant, tenant3, tenant4);
    }

    /**
     * Configure and build the connection template
     *
     * @param tenant the client
     * @param url    the JDBC url
     * @return JdbcTemplate
     */
    private JdbcTemplate createTemplate(Tenant tenant, String url) {
        HikariConfig config = new HikariConfig();
        config.setMaximumPoolSize(10);
        config.setPoolName(tenant.getTenantId() + "-pool");
        config.setPassword(tenant.getPassword());
        config.setUsername(tenant.getUsername());
        config.setConnectionTimeout(10_000);
        config.setJdbcUrl(url);
        config.setDriverClassName(tenant.getDriver());
        //config.setConnectionInitSql("");
        config.setMinimumIdle(2);
        return new JdbcTemplate(new HikariDataSource(config));
    }
}
