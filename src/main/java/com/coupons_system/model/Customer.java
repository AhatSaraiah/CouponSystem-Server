package com.coupons_system.model;



import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.JoinColumn;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@DynamicUpdate
@Entity
@Table(name = "Customers")
public class Customer {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<Coupon> coupons = new HashSet<>();

    public Customer() {}

    public Customer(int id, String firstName, String lastName, String email, String password, Set<Coupon> coupons) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.coupons = coupons;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "FIRST_NAME", nullable = false)
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "LAST_NAME", nullable = false)
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "Email", nullable = false)
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "PASSWORD", nullable = false)
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToMany
    @JoinTable(name = "customers_vs_coupons", 
        joinColumns = @JoinColumn(name = "CUSTOMER_ID", updatable = false),
        inverseJoinColumns = @JoinColumn(name = "COUPON_ID", updatable = false))
    @JsonManagedReference
    public Set<Coupon> getCoupons() {
        return coupons;
    }
    
    
    public void setCoupons(Set<Coupon> coupons) {
        this.coupons = coupons;
    }

    @Override
    public String toString() {
        return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
                + ", password=" + password + ", coupons=" + coupons + "]";
    }
}
