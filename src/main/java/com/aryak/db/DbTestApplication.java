package com.aryak.db;

import com.aryak.db.rowmappers.ProductRowMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
@Slf4j
public class DbTestApplication {

	private final JdbcTemplate h2Template;
	private final JdbcTemplate mysqlTemplate;

	public DbTestApplication(JdbcTemplate h2Template, JdbcTemplate mySqlTemplate) {
		this.h2Template = h2Template;
		this.mysqlTemplate = mySqlTemplate;
	}

	public static void main(String[] args) {
		SpringApplication.run(DbTestApplication.class, args);
	}

	//@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			var products = h2Template.query("select * from products", new ProductRowMapper());
			log.info("Products : {}", products);
		};
	}

	@Bean
	public CommandLineRunner commandLineRunner2() {
		return args -> {
			var products = mysqlTemplate.query("select * from products", new ProductRowMapper());
			log.info("Products : {}", products);
		};
	}
}
