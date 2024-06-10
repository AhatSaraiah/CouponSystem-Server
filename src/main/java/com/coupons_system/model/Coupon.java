package com.coupons_system.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonBackReference;

@DynamicUpdate
@Entity
@Table(name = "Coupons")
public class Coupon {

    private int id;
    private Company company;
    private Set<Customer> customers = new HashSet<>();
    private Category category;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private int amount;
    private double price;
    private String image;

    public Coupon() {}

    public Coupon(int id, Company company, Set<Customer> customers, Category category, String title, String description, Date startDate,
                  Date endDate, int amount, double price, String image) {
        this.id = id;
        this.company = company;
        this.customers = customers;
        this.category = category;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
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

    @ManyToMany(mappedBy = "coupons", fetch = FetchType.LAZY)
    @JsonBackReference
    public Set<Customer> getCustomers() {
        return customers;
    }
    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "COMPANY_ID", updatable = false, nullable = false)
    @JsonBackReference
    public Company getCompany() {
        return company;
    }
    public void setCompany(Company company) {
        this.company = company;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "CATEGORY", nullable = false)
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

    @Column(name = "TITLE", nullable = false)
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "DESCRIPTION", nullable = true)
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "START_DATE", nullable = false)
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "END_DATE", nullable = false)
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Column(name = "AMOUNT", nullable = false, columnDefinition = "integer")
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Column(name = "PRICE", nullable = false, columnDefinition = "double")
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    @Column(name = "IMAGE", nullable = true)
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Coupon [id=" + id + ", companyId=" + company.getId() + ", customers=" + customers + ", category=" + category + ", title=" + title
                + ", description=" + description + ", startDate=" + startDate + ", endDate=" + endDate + ", amount="
                + amount + ", price=" + price + ", image=" + image + "]";
    }
}