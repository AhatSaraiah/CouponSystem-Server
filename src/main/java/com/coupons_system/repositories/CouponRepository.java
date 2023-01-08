package com.coupons_system.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.coupons_system.model.Category;
import com.coupons_system.model.Coupon;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {
	
	@Query(value="SELECT c FROM coupons c WHERE c.price<= ?1 and c.customerId = ?2", nativeQuery = true)
	public Set<Coupon> findAllByMaxPrice(double maxPrice,int customerId);
	@Query(value="SELECT c FROM coupons c WHERE  c.customerId = ?1", nativeQuery = true)
	public Set<Coupon> findAllByCustomerId(int customerId);
	@Query(value="SELECT c FROM coupons c WHERE  c.customerId = ?1 and c.category = ?2 ", nativeQuery = true)
	public Set<Coupon> findAllByCustomerIdAndCategory(int customerId,Category category);
	
	@Query(value="SELECT c>0 FROM coupons c WHERE  c.customerId = ?1 ", nativeQuery = true)
	public boolean existsByCustomerId(int customerId);
	
	@Query(value="SELECT c FROM coupons c WHERE c.price<= ?1 and c.companyId = ?2", nativeQuery = true)
	public Set<Coupon> findByPrice(double maxPrice,int companyId);
	
	@Query(value="SELECT c FROM coupons c WHERE  c.companyId = ?1", nativeQuery = true)
	public Set<Coupon> findByCompanyId(int companyId);
	@Query(value="SELECT c FROM coupons c WHERE  c.companyId = ?1 and c.category = ?2 ", nativeQuery = true)
	public Set<Coupon> findByCompanyIdAndCategory(int companyId,Category category);
	
	@Query(value="SELECT c>0 FROM coupons c WHERE  c.companyId = ?1 and c.title = ?2", nativeQuery = true)
	public boolean existsByCompanyIdAndTitle(int companyId,String t);
	
}
