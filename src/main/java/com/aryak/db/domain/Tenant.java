package com.aryak.db.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tenant {

    private String tenantId;
    private DatabaseType dbType;
    private String version;
    private String driver;
    private String host;
    private int port;
    private String databaseName;
    private String username;
    private String password;

}
