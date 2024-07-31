package com.aryak.db;

import com.aryak.db.dao.ProductDao;
import com.aryak.db.domain.Product;
import com.aryak.db.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@EnableScheduling
@SpringBootApplication
@Slf4j
public class DbTestApplication {

    private final ProductDao productDao;
    ExecutorService executor = Executors.newFixedThreadPool(20);
    static AtomicInteger id = new AtomicInteger(4);

    public DbTestApplication(ProductDao productDao) {
        this.productDao = productDao;
    }

    public static void main(String[] args) {
        SpringApplication.run(DbTestApplication.class, args);
    }

    @Scheduled(fixedDelay = 5000)
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

}
