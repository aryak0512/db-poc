package com.aryak.db.service.impl;

import com.aryak.db.domain.Product;
import com.aryak.db.multitenancy.TenantContext;
import com.aryak.db.multitenancy.TenantStore;
import com.aryak.db.repository.ProductRepository;
import com.aryak.db.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final TenantStore tenantStore;

    public ProductServiceImpl(ProductRepository productRepository, TenantStore tenantStore) {
        this.productRepository = productRepository;
        this.tenantStore = tenantStore;
    }

    @Override
    public List<Product> getAll() {
        var template = tenantStore.getTemplate(TenantContext.getCurrentTenant());
        return productRepository.findAll(template);
    }

    @Override
    public Product getById(int id) {
        var template = tenantStore.getTemplate(TenantContext.getCurrentTenant());
        return productRepository.findById(template, id);
    }

    @Override
    public Product addProduct(Product product) {
        var template = tenantStore.getTemplate(TenantContext.getCurrentTenant());
        return productRepository.save(template, product);
    }
}
