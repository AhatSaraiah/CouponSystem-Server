package com.coupons_system.service;


import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coupons_system.model.Category;
import com.coupons_system.model.Coupon;
import com.coupons_system.model.Customer;
import com.coupons_system.repositories.CompanyRepository;
import com.coupons_system.repositories.CouponRepository;
import com.coupons_system.repositories.CustomerRepository;
import com.coupons_system.utils.MyException;

@Service
public class CustomerServicesimpl extends ClientServicesimpl implements CustomerServices {
	private int customerId;
    @Autowired
	private  CompanyRepository comRepo;
    @Autowired
	private CustomerRepository custRepo;
    @Autowired
	private CouponRepository coupRepo;
	public CustomerServicesimpl(int customerId,CompanyRepository comRepo, CustomerRepository custRepo, CouponRepository coupRepo) {
		//super(comRepo, custRepo, coupRepo);
		this.customerId=customerId;
		this.comRepo=comRepo;
		this.coupRepo=coupRepo;
		this.custRepo=custRepo;
		
	}
	public CustomerServicesimpl() {
		
	}
	@Override
	public boolean login(String email, String password) {
		try {

			if(custRepo.existsByEmailAndPassword(email,password))
				return true;
			else if(custRepo.existsByEmail(email)) 
				throw new MyException("The password is not correct");
			else
				throw new MyException("The email is not exists");

		}catch (MyException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	@Override
	public void addCouponPurchase(Coupon coupon) {
		Set <Coupon> coupons=getCustomerCoupons();
		Customer customer=getCustomerDetails();
		try {
			if(coupRepo.existsByCustomersId(customerId)) 		
				throw new MyException("The coupon is exist!");
			else if(coupon.getAmount()==0) 
				throw new MyException("Coupons have expired !");
			else if(couponExpired(coupon)) 
				throw new MyException("Coupon date has expired !");
			else {
				coupons.add(coupon);
				coupon.setAmount(coupon.getAmount()-1);
				customer.setCoupons(coupons);
				coupRepo.saveAll(coupons);
			}
		}catch (MyException e) {
			System.out.println(e.getMessage());
		}
	}
	@Override
	public boolean couponExpired(Coupon coupon) {
		Instant todayDate=ZonedDateTime.now().toInstant();
		Instant endDate = coupon.getEndDate().toInstant();
		if(endDate.isAfter(todayDate)) 
			return true;
		return false;
	}
	@Override
	public Customer getCustomerDetails() {
		return custRepo.getOne(customerId);
	}
	@Override
	public Set<Coupon> getCustomerCoupons(Category category)  {
		return coupRepo.findByCustomersIdAndCategory(customerId,category);
	}

	@Override
	public Set<Coupon> getCustomerCoupons(double maxPrice)  {
		return coupRepo.findAllByPriceLessThanEqualAndCustomersId(maxPrice,customerId);
	}

	@Override
	public  Set<Coupon> getCustomerCoupons()  {
		return coupRepo.findByCustomersId(customerId);
	}
	
	@Override
	public java.util.List<Coupon> getAllCoupons() {
		return coupRepo.findAll();
	}

}
