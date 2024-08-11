package com.aryak.db;

import com.aryak.db.dao.ProductDao;
import com.aryak.db.domain.Product;
import com.aryak.db.multitenancy.MultitenancyConfiguration;
import com.aryak.db.utils.ConfigManager;
import com.aryak.db.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static com.aryak.db.utils.Constants.BULKSMS_HOST;

@EnableScheduling
@SpringBootApplication
@Slf4j
public class DbTestApplication {


    @Autowired
    ConfigManager configManager;

    @Autowired
    protected MultitenancyConfiguration multitenancyConfiguration;

    private final ProductDao productDao;
    static AtomicInteger id = new AtomicInteger(4);

    public DbTestApplication(ProductDao productDao) {
        this.productDao = productDao;
    }

    public static void main(String[] args) {
        SpringApplication.run(DbTestApplication.class, args);
    }

    //@Scheduled(fixedDelay = 5000)
    public void doTask() throws InterruptedException {
        simulateLoad();
    }

    private void scheduledTask() {
        IntStream.rangeClosed(1, 30).forEach(i -> {
            Thread.ofPlatform().start(() -> {

                Product p = new Product("product 1", id.getAndIncrement(), 46.00);
                productDao.save(p);

                var products = productDao.findAll();
                log.info("Products : {}", products);
            });
        });
    }


    public void simulateLoad() {
        IntStream.rangeClosed(1, 500)
				.forEach(i -> Constants.dbInsertExecutor.execute(() -> {
					Product p = new Product("product 1", id.getAndIncrement(), 46.00);
					productDao.save(p);
				}));
    }

   // @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            configManager.reloadProps();
            var property = configManager.getProperty(BULKSMS_HOST);
            log.info("Test : {}", property);
        };
    }

    //@Scheduled(fixedDelay = 2000)
    public void print(){
        configManager.reloadProps();
        var property = configManager.getProperty("bulksms.host");
        //System.out.println(property);
    }

    @Bean
    public CommandLineRunner commandLineRunner11() {
        return args -> {
            // do something at startup
            multitenancyConfiguration.loadTenants();
        };
    }
}
