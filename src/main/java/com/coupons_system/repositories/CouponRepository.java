package com.coupons_system.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.coupons_system.model.Category;
import com.coupons_system.model.Coupon;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {
	
	public Set<Coupon> findAllByPriceLessThanEqualAndCustomersId(double maxPrice,int customerId);
	
	public Set<Coupon> findByCustomersId(int customerId);
	
	public Set<Coupon> findByCustomersIdAndCategory(int customerId,Category category);
	
	public boolean existsByCustomersId(int customerId);
	
	public Set<Coupon> findByPriceLessThanEqualAndCompanyId(double maxPrice,int companyId);
	
	public Set<Coupon> findByCompanyId(int companyId);

	public Set<Coupon> findByCompanyIdAndCategory(int companyId,Category category);
	
	public boolean existsByCompanyIdAndTitle(int companyId,String title);
	
}
