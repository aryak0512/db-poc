package com.aryak.db.multitenancy;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MultitenantDatasource extends AbstractRoutingDataSource {

    @Override
    protected String determineCurrentLookupKey() {
        return TenantContext.getCurrentTenant();
    }

}