package com.coupons_system.service;


import java.util.Set;

import com.coupons_system.model.Category;
import com.coupons_system.model.Coupon;
import com.coupons_system.model.Customer;

public interface CustomerServices {

public Customer getCustomerDetails();
public void addCouponPurchase(Coupon coupon);
public boolean couponExpired(Coupon coupon);
public Set<Coupon> getCustomerCoupons(Category category);
public Set<Coupon> getCustomerCoupons(double maxPrice);
public Set<Coupon> getCustomerCoupons();
public boolean login(String email, String password);
public java.util.List<Coupon> getAllCoupons();

}