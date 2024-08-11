package com.aryak.db.multitenancy;

import com.aryak.db.domain.Tenant;
import com.aryak.db.exceptions.ClientNotFoundException;
import lombok.Getter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TenantStore {

    @Getter
    private final Map<String, Tenant> tenantMap = new ConcurrentHashMap<>();

    @Getter
    private final Map<String, JdbcTemplate> tenantConfig = new ConcurrentHashMap<>();

    public void put(Tenant tenant) {
        tenantMap.put(tenant.getTenantId(), tenant);
    }

    public void put(Tenant tenant, JdbcTemplate jdbcTemplate) {
        tenantConfig.put(tenant.getTenantId(), jdbcTemplate);
    }

    public JdbcTemplate getTemplate(Tenant tenant) {
        return tenantConfig.get(tenant.getTenantId());
    }

    public JdbcTemplate getTemplate(String tenantId) {
        return tenantConfig.get(tenantId);
    }

    public Tenant getClient(String clientId) {

        return Optional.ofNullable(tenantMap.get(clientId))
                .orElseThrow(() -> new ClientNotFoundException("Client with client id : " + clientId + " not found!"));
    }
}
