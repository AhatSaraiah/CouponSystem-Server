package com.coupons_system.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.coupons_system.model.Category;
import com.coupons_system.model.Company;
import com.coupons_system.model.Coupon;
import com.coupons_system.service.CompanyServices;


@RestController
@RequestMapping("/company")
@CrossOrigin(origins = "http://localhost:4200")
public class CompanyController extends ClientController{
	@Autowired
	private CompanyServices companyServices;

	public CompanyController() {
		super();
	}

	@Override
	@GetMapping(path="/loginCompany/{email}/{password}",produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean login(@PathVariable("email")  String email,@PathVariable("password")  String password) {
		return companyServices.login(email, password);
	}

	@PostMapping(path="/addCoupon",consumes = MediaType.APPLICATION_JSON_VALUE)
	public void addCoupon(@RequestBody Coupon coupon) {
		companyServices.addCoupon(coupon);
	}
	@PutMapping(path="/updateCoupon",consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateCoupon(@RequestBody Coupon coupon) {
		companyServices.updateCoupon(coupon);
	}
	@DeleteMapping(path="/deleteCoupon/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteCoupon(@PathVariable("id")  int couponId) {
		companyServices.deleteCoupon(couponId);
	}

	@GetMapping(path="/getCompanyCoupons",produces = MediaType.APPLICATION_JSON_VALUE)
	public Set<Coupon> getCompanyCoupons() {
		return companyServices.getCompanyCoupons();

	}
	@GetMapping(path="/getCompanyDetails",produces = MediaType.APPLICATION_JSON_VALUE)
	public Company getCompanyDetails() {
		return companyServices.getCompanyDetails();

	}

	@GetMapping(path="/getCompanyCoupons/{category}",produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public Set<Coupon> getCompanyCoupons(@PathVariable("category")  Category category) {
		return companyServices.getCompanyCoupons(category);

	}
	@GetMapping(path="/getCompanyCoupons/{maxPrice}",produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public Set<Coupon> getCompanyCoupons(@PathVariable("maxPrice") double maxPrice) {
		return companyServices.getCompanyCoupons(maxPrice);

	}

}
