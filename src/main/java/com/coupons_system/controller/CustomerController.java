package com.coupons_system.controller;


import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.coupons_system.model.Category;
import com.coupons_system.model.Coupon;
import com.coupons_system.model.Customer;
import com.coupons_system.service.CustomerServices;


@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerController extends ClientController{

   @Autowired
   private CustomerServices customerServices;
	   
	public CustomerController() {
		super();
		// TODO Auto-generated constructor stub
	}

    @Override
	@GetMapping(path="/loginCustomer/{email}/{password}",produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean login(@PathVariable("email") String email,@PathVariable("password") String password) {
		return customerServices.login(email, password);
	}
    
	@PostMapping(path="/addCouponPurchase",consumes = MediaType.APPLICATION_JSON_VALUE)
	public void addCouponPurchase(@RequestBody Coupon coupon) {
		customerServices.addCouponPurchase(coupon);
	}
	
	@GetMapping(path="/getCustomerCoupons",produces = MediaType.APPLICATION_JSON_VALUE)
	public Set<Coupon> getCustomerCoupons() {
	    return customerServices.getCustomerCoupons();

	}
	
	@GetMapping(path="/getCustomerCoupons/{category}",produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public Set<Coupon> getCustomerCoupons(@PathVariable("category") Category category) {
	    return customerServices.getCustomerCoupons(category);

	}
	@GetMapping(path="/getCustomerCoupons/{maxPrice}",produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public Set<Coupon> getCustomerCoupons(@PathVariable("maxPrice") double maxPrice) {
	    return customerServices.getCustomerCoupons(maxPrice);

	}
	
	@GetMapping(path="/getCustomerDetails",produces = MediaType.APPLICATION_JSON_VALUE)
	public Customer getCustomerDetails() {
	    return customerServices.getCustomerDetails();

	}
	
	@GetMapping(path="/getAllCoupons",produces = MediaType.APPLICATION_JSON_VALUE)
	public java.util.List<Coupon> getAllCoupons() {
	    return customerServices.getAllCoupons();
	}

}
