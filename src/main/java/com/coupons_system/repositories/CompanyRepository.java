package com.coupons_system.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coupons_system.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
	public boolean existsByEmailAndPassword(String email, String password);
	public boolean existsByEmailOrName(String email, String name);
	public boolean existsByEmail(String email);

}
