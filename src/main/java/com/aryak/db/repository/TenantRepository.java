package com.aryak.db.repository;

import com.aryak.db.domain.Product;
import com.aryak.db.domain.Tenant;
import com.aryak.db.multitenancy.TenantStore;
import com.aryak.db.rowmappers.ProductRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TenantRepository {

    private final TenantStore tenantStore;

    public TenantRepository(TenantStore tenantStore) {
        this.tenantStore = tenantStore;
    }

    public List<Product> fetchAll(Tenant tenant) {
        var template = tenantStore.getTemplate(tenant);
        return template.query("select * from products", new ProductRowMapper());
    }
}
