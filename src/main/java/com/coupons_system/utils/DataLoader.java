package com.coupons_system.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.coupons_system.model.Company;
import com.coupons_system.model.Customer;
import com.coupons_system.service.AdminServices;

import java.util.Iterator;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private AdminServices adminServices;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Checking data...");
        try {
            Iterable<Company> companies = adminServices.getAllCompanies();
            if (!companies.iterator().hasNext()) {
                System.out.println("No companies found. Seeding realistic data...");
                
                adminServices.addCompany(new Company(0, "Apple", "contact@apple.com", "App!e2025", null));
                adminServices.addCompany(new Company(0, "Samsung", "support@samsung.com", "S@msung25", null));
                adminServices.addCompany(new Company(0, "Tesla", "elon@tesla.com", "T3slaRisk", null));
                adminServices.addCompany(new Company(0, "Starbucks", "coffee@starbucks.com", "C0ffeeLvr", null));
                adminServices.addCompany(new Company(0, "Amazon", "jeff@amazon.com", "Prim3Day!", null));
                adminServices.addCompany(new Company(0, "CocaCola", "info@coca-cola.com", "TasTeTh3F", null));
                adminServices.addCompany(new Company(0, "Netflix", "binge@netflix.com", "Chill&W@tch", null));
                adminServices.addCompany(new Company(0, "Spotify", "music@spotify.com", "Li5tenUp!", null));
                
                System.out.println("Seeded 8 realistic companies.");
            }

            Iterable<Customer> customers = adminServices.getAllCustomers();
            if (!customers.iterator().hasNext()) {
                System.out.println("No customers found. Seeding realistic data...");
                
                adminServices.addCustomer(new Customer(0, "Emma", "Stone", "emma.stone@gmail.com", "LaLaL@nd", null));
                adminServices.addCustomer(new Customer(0, "Tom", "Holland", "tom.holland@marvel.com", "Spid3y!", null));
                adminServices.addCustomer(new Customer(0, "Robert", "Downey", "rdj@stark.com", "IronM@n1", null));
                adminServices.addCustomer(new Customer(0, "Scarlett", "Johansson", "black.widow@avengers.com", "Nat@sha", null));
                adminServices.addCustomer(new Customer(0, "Chris", "Evans", "cap@america.com", "Shield123", null));
                adminServices.addCustomer(new Customer(0, "Zendaya", "Coleman", "zendaya@euphoria.com", "RuuRuu!", null));
                
                System.out.println("Seeded 6 realistic customers.");
            }
        } catch (Exception e) {
            System.err.println("Error seeding data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
