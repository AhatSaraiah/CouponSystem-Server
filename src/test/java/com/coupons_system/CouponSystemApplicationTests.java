package com.coupons_system;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.coupons_system.CouponSystemApplication;

@SpringBootTest(classes = CouponSystemApplication.class)
@ActiveProfiles("test")
class CouponSystemApplicationTests {

	@Test
	void contextLoads() {
	}

}
