package com.aryak.db.controller;

import com.aryak.db.domain.Tenant;
import com.aryak.db.multitenancy.TenantContext;
import com.aryak.db.multitenancy.TenantStore;
import com.aryak.db.repository.TenantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
}
