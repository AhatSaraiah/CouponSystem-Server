package com.coupons_system.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coupons_system.model.Category;
import com.coupons_system.model.Company;
import com.coupons_system.model.Coupon;
import com.coupons_system.model.Customer;
import com.coupons_system.repositories.CompanyRepository;
import com.coupons_system.repositories.CouponRepository;
import com.coupons_system.repositories.CustomerRepository;
import com.coupons_system.utils.MyException;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CompanyServicesimpl extends ClientServicesimpl implements CompanyServices {

	private int companyId;
	@Autowired
	private  CompanyRepository comRepo;
	@Autowired
	private CustomerRepository custRepo;
	@Autowired
	private CouponRepository coupRepo;
	public CompanyServicesimpl(int companyId,CompanyRepository comRepo, CustomerRepository custRepo, CouponRepository coupRepo) {
		//super(comRepo, custRepo, coupRepo);
		this.companyId=companyId;
		this.comRepo=comRepo;
		this.coupRepo=coupRepo;
		this.custRepo=custRepo;
	}
	
	public CompanyServicesimpl() {
		super();
	}
	@Override
	public boolean login(String email, String password) {
		try {

			if(comRepo.existsByEmailAndPassword(email,password)) {
				Company c = comRepo.findByEmail(email);
				if (c != null) {
					this.companyId = c.getId();
					System.out.println("Company Logged in. ID: " + this.companyId);
					return true;
				}
			}
			else if(comRepo.existsByEmail(email)) 
				throw new MyException("The password is not correct");
			else
				throw new MyException("The email is not exists");

		}catch (MyException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	@Override
	public void addCoupon(Coupon coupon) {	
		// Verify logged in
		if (this.companyId == 0) {
			throw new RuntimeException("You are not logged in as a company!");
		}

		try {
			if(coupRepo.existsByCompanyIdAndTitle(companyId, coupon.getTitle()))
				throw new RuntimeException("The coupon title is exists in this company");
			else {
				// Link the coupon to the company (Critical for Foreign Key)
				Company company = getCompanyDetails();
				coupon.setCompany(company);
				
				// Do not subtract amount on creation
				// coupon.setAmount(coupon.getAmount()); 
				
				coupRepo.save(coupon);
				System.out.println("Coupon saved successfully: " + coupon.getTitle());
			}
		}catch (Exception e) {
			System.out.println("Error adding coupon: " + e.getMessage());
			throw new RuntimeException(e.getMessage());
		}

	}


	@Override
	public void updateCoupon(Coupon coupon) {
		
         coupRepo.save(coupon);
	}

	@Override
	public void deleteCoupon(int couponID) {
		List<Customer> customers=custRepo.findAll();
		Coupon coupon=coupRepo.getOne(couponID);
		for(Customer c: customers) {
			custRepo.getOne(c.getId()).getCoupons().remove(coupon);
		    coupon.setAmount(coupon.getAmount()+1);
		}
		comRepo.getOne(companyId).getCoupons().remove(coupon);
		
	       
	}


	@Override
	public Company getCompanyDetails() {
		return comRepo.getOne(companyId);
	}
	@Override
	public Set<Coupon> getCompanyCoupons(Category category)  {
		return coupRepo.findByCompanyIdAndCategory(companyId,category);
	}

	
	@Override
	public Set<Coupon> getCompanyCoupons(double maxPrice)  {
		return coupRepo.findByPriceLessThanEqualAndCompanyId(maxPrice,companyId);
	}

	@Override
	public  Set<Coupon> getCompanyCoupons()  {
		return coupRepo.findByCompanyId(companyId);

	}
}

