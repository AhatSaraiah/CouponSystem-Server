package com.coupons_system.service;

import java.util.List;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coupons_system.model.Company;
import com.coupons_system.model.Coupon;
import com.coupons_system.model.Customer;
import com.coupons_system.repositories.CompanyRepository;
import com.coupons_system.repositories.CouponRepository;
import com.coupons_system.repositories.CustomerRepository;
import com.coupons_system.utils.MyException;

@Service
public class AdminServicesimpl extends ClientServicesimpl implements  AdminServices {

		//super(comRepo, custRepo, coupRepo);
	    @Autowired
		private  CompanyRepository comRepo;
	    @Autowired
		private CustomerRepository custRepo;
	    @Autowired
		private CouponRepository coupRepo;
	

		public AdminServicesimpl(CompanyRepository comRepo,CustomerRepository custRepo,CouponRepository coupRepo) {
			this.comRepo=comRepo;
			this.coupRepo=coupRepo;
			this.custRepo=custRepo;
		}
	public AdminServicesimpl() {
		super();
	}
	
	@Override
	public boolean login(String email, String password) {
		try {
	        if (!email.equals("admin@admin.com") || !password.equals("admin")) {
				throw new MyException("The email or password  are not correct");
	        }else {
			    return true;
			}
		}catch (MyException e) {
			System.out.println(e.getMessage());
			return false;
		}

	}
	
	@Override
	public void addCompany(Company company) {
		try{
			if(comRepo.existsByEmailOrName(company.getName(),company.getEmail())) 
				throw new MyException("The company name/email is exist!");
			else
				comRepo.save(company);	
			
		}catch (MyException e) {
			System.out.println(e.getMessage());
		}
	}


	@Override
	public Iterable<Company> getAllCompanies() {
		return comRepo.findAll();
	}

	
	@Override
	public Company getOneCompany(int id) {
		return comRepo.getOne(id);
	}


	
	@Override
	public Boolean isCompanyExists(int companyId) {
		return comRepo.existsById(companyId);
	}

	
	@Override
	public void updateCompany(Company company) {
		try{
			if(comRepo.existsById(company.getId()))
				comRepo.save(company);		
			else
				throw new MyException("The company is not exist");

		}catch (MyException e) {
			System.out.println(e.getMessage());
		}
	}

	
	@Override
	public void deleteCompany(int companyID) {
		Set<Coupon>coupons=coupRepo.findByCompanyId(companyID);
		List<Customer>customers= getAllCustomers();
		for(Coupon coupon: coupons) {
		   for(Customer c: customers) {
			custRepo.getOne(c.getId()).getCoupons().remove(coupon);
		   }
		}
		
		comRepo.deleteById(companyID);		
	}


	@Override
	public void addCustomer(Customer customer) {
		
		try{
			if(custRepo.existsByEmailOrFirstName(customer.getEmail(),customer.getFirstName()))
					throw new MyException("The customer email/name is exist!");
			else
			       custRepo.save(customer);		
		}catch (MyException e) {
			System.out.println(e.getMessage());
		}	
		
	}

	@Override
	public void updateCustomer(Customer customer) {
		try{
			if(custRepo.existsById(customer.getId()))
				throw new MyException("The customer email is exist!");
			else
				custRepo.save(customer);		
		}catch (MyException e) {
			System.out.println(e.getMessage());
		}	
	}

	
	@Override
	public void deleteCustomer(int customerID) {
		Customer customer=getOneCustomer(customerID);
		Set<Coupon>coupons=customer.getCoupons();
		for(Coupon coupon: coupons) {
			customer.getCoupons().remove(coupon);
		    coupon.setAmount(coupon.getAmount()+1);
		}
		custRepo.deleteById(customerID);		
		
	}

	@Override
	public List <Customer> getAllCustomers() {
		return custRepo.findAll();
	}

	@Override
	public Customer getOneCustomer(int customerID) {
		return custRepo.getOne(customerID);
	}



}
