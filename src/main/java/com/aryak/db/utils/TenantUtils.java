package com.aryak.db.utils;

import com.aryak.db.domain.DatabaseType;
import com.aryak.db.domain.Tenant;

public class TenantUtils {

    /**
     * @param tenant
     * @return
     */
    public static String getUrl(Tenant tenant) {

        // jdbc:mysql://localhost:3306/eshop
        // jdbc:postgresql://192.168.1.170:5432/sample?ssl=true
        // jdbc:oracle:thin:@localhost:1521:orcl

        String url = "jdbc:{DB_TYPE}://{HOST}:{PORT}/{DATABASE_NAME}?ssl=false";
        return url.replace("{DB_TYPE}", getDBtype(tenant))
                .replace("{HOST}", tenant.getHost())
                .replace("{PORT}", String.valueOf(tenant.getPort()))
                .replace("{DATABASE_NAME}", tenant.getDatabaseName());
    }

    private static String getDBtype(Tenant tenant) {

        if ( tenant.getDbType() == DatabaseType.MYSQL ) {
            return "mysql";
        } else if ( tenant.getDbType() == DatabaseType.POSTGRES ) {
            return "postgresql";
        } else {
            return "oracle";
        }
    }
}
