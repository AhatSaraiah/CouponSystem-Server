package com.coupons_system.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.coupons_system.model.Company;
import com.coupons_system.model.Customer;
import com.coupons_system.service.AdminServices;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController extends ClientController {
	   @Autowired
	   private AdminServices adminServices;
	public AdminController() {
		super();
	}
    @Override
	@GetMapping(path="/loginAdmin/{email}/{password}", produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean login(@PathVariable("email") String email,@PathVariable("password") String password) {
		return adminServices.login(email, password);
	}
	@PostMapping(path="/addCompany",consumes = MediaType.APPLICATION_JSON_VALUE)
	public void addCompany(@RequestBody Company company) {
		adminServices.addCompany(company);
	}
	@PutMapping(path="/updateCompany",consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateCompany(@RequestBody Company company) {
		adminServices.updateCompany(company);
	}
	@DeleteMapping(path="/deleteCompany/{id}")
	public void deleteCompany(@PathVariable("id") int companyId) {
		adminServices.deleteCompany(companyId);
	}
//	@GetMapping(path="/getAllCompanies",produces = MediaType.APPLICATION_JSON_VALUE)
//	public ArrayList<Company> getAllCompanies() {
//	    return (ArrayList<Company>) adminServices.getAllCompanies();
//
//	}
	@GetMapping(path="/getAllCompanies",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Company> getAllCompanies() {
	    Iterable<Company> companies = adminServices.getAllCompanies();
	    List<Company> companyList = new ArrayList<>();
	    companies.forEach(companyList::add);
	    return companyList;
	}
	
	@GetMapping(path="/getOneCompany/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
	public Company getOneCompany(@PathVariable("id") int id){
	    return adminServices.getOneCompany(id);
	}
	
    @PostMapping(path="/addCustomer",consumes = MediaType.APPLICATION_JSON_VALUE)
	public void addCustomer(@RequestBody Customer customer) {
		adminServices.addCustomer(customer);
	}
	@PutMapping(path="/updateCustomer",consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateCustomer(@RequestBody Customer customer) {
		adminServices.updateCustomer(customer);
	}
	@DeleteMapping(path="/deleteCustomer/{id}")
	public void deleteCustomer(@PathVariable("id") int customerId) {
		adminServices.deleteCustomer(customerId);
	}
	@GetMapping(path="/getAllCustomers",produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<Customer> getAllCustomers() {
	    return (ArrayList<Customer>) adminServices.getAllCustomers();

	}
	@GetMapping(path="/getOneCustomer/{id}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public Customer getOneCustomer(@PathVariable int id){
	    return adminServices.getOneCustomer(id);
	}
	
}
