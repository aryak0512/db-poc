package com.aryak.db.multitenancy;

import com.aryak.db.domain.Tenant;
import com.aryak.db.utils.ConfigManager;
import com.aryak.db.utils.Constants;
import com.aryak.db.utils.TenantUtils;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
public class MultitenancyConfiguration {

    private final TenantStore tenantStore;
    private final ConfigManager configManager;

    public MultitenancyConfiguration(TenantStore tenantStore, ConfigManager configManager) {
        this.tenantStore = tenantStore;
        this.configManager = configManager;
    }

    public void loadTenants() {

        // https://www.baeldung.com/multitenancy-with-spring-data-jpa

        List<Tenant> tenants = getAllTenants();
        if ( tenants == null ) {
            throw new RuntimeException("No tenant found!");
        }

        tenants.parallelStream()
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
     * Obtains client information from remote service via REST call
     *
     * @return list of all tenant configuration
     */
    private List<Tenant> getAllTenants() {

        WebClient webClient = WebClient.builder()
                .baseUrl(configManager.getProperty(Constants.TENANT_LOAD_URL))
                .build();

        Mono<List<Tenant>> tenantsMono = webClient.get()
                .uri("/tenantConfig")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });

        return tenantsMono.block();

    }

    /**
     * Configure and build the connection template
     *
     * @param tenant the client
     * @param url    the JDBC url
     * @return JdbcTemplate
     * @see https://github.com/brettwooldridge/HikariCP
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
