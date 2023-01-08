package com.coupons_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.coupons_system.utils.Test;

@SpringBootApplication
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.coupons_system.model"} )
@EnableJpaRepositories(basePackages = {"com.coupons_system.repositories"})
public class CouponSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CouponSystemApplication.class, args);
		//Test test = Test.getInstance();
		//test.TestAll();
	}

}
