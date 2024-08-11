package com.aryak.db.controller;

import com.aryak.db.domain.DatabaseType;
import com.aryak.db.domain.Tenant;
import com.aryak.db.multitenancy.TenantContext;
import com.aryak.db.multitenancy.TenantStore;
import com.aryak.db.repository.TenantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
public class TenantController {

    private final TenantRepository repository;
    private TenantStore tenantStore;

    public TenantController(TenantRepository repository, TenantStore tenantStore) {
        this.repository = repository;
        this.tenantStore = tenantStore;
    }

    @GetMapping(value = "/v1/client/{clientId}")
    public void test(@PathVariable String clientId) {
        TenantContext.setCurrentTenant(clientId);
        // get current client's datasource
        Tenant tenant = tenantStore.getClient(clientId);
        repository.fetchAll(tenant).stream().forEach(p -> log.info("Product : {}", p));
        log.info("Business logic executing for client : {}", clientId);
        TenantContext.clear();
    }

    @GetMapping(value = "/tenantConfig")
    public ResponseEntity<List<Tenant>> test() {

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

        var data = List.of(tenant2, tenant, tenant3, tenant4);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
