# Coupon System - Backend

Spring Boot REST API for the Coupon Management System.

## 🛠️ Tech Stack

- **Spring Boot 2.4.2** - Application framework
- **Spring Data JPA** - Data persistence
- **Hibernate 5.4** - ORM
- **MySQL 8.0** - Database
- **Maven 3.9** - Build tool
- **HikariCP** - Connection pooling
- **Jackson** - JSON serialization
- **Lombok** - Boilerplate reduction

## 📦 Dependencies

```xml
<dependencies>
    <!-- Spring Boot Starters -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    
    <!-- Database -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
    </dependency>
    
    <!-- DevTools -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
    </dependency>
</dependencies>
```

## 🏗️ Architecture

### **Layered Architecture**

```
com.coupons_system/
├── controller/          # REST API endpoints (@RestController)
│   ├── AdminController.java
│   ├── CompanyController.java
│   └── CustomerController.java
│
├── service/            # Business logic (@Service)
│   ├── AdminServicesimpl.java
│   ├── CompanyServicesimpl.java
│   └── CustomerServicesimpl.java
│
├── repositories/       # Data access (@Repository)
│   ├── CompanyRepository.java
│   ├── CustomerRepository.java
│   └── CouponRepository.java
│
├── model/             # JPA entities (@Entity)
│   ├── Company.java
│   ├── Customer.java
│   ├── Coupon.java
│   └── Category.java (enum)
│
└── utils/             # Utilities
    ├── DataLoader.java      # Seed data on startup
    └── MyException.java     # Custom exception
```

## 🗄️ Database Schema

### **Tables**

#### **Companies**
```sql
CREATE TABLE Companies (
    id INT PRIMARY KEY AUTO_INCREMENT,
    NAME VARCHAR(255) NOT NULL,
    Email VARCHAR(255) NOT NULL UNIQUE,
    PASSWORD VARCHAR(255) NOT NULL
);
```

#### **Customers**
```sql
CREATE TABLE Customers (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    FIRST_NAME VARCHAR(255) NOT NULL,
    LAST_NAME VARCHAR(255) NOT NULL,
    Email VARCHAR(255) NOT NULL UNIQUE,
    PASSWORD VARCHAR(255) NOT NULL
);
```

#### **Coupons**
```sql
CREATE TABLE Coupons (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    COMPANY_ID INT NOT NULL,
    CATEGORY INT NOT NULL,
    TITLE VARCHAR(255) NOT NULL,
    DESCRIPTION TEXT,
    START_DATE DATE NOT NULL,
    END_DATE DATE NOT NULL,
    AMOUNT INT NOT NULL,
    PRICE DOUBLE NOT NULL,
    IMAGE VARCHAR(255),
    FOREIGN KEY (COMPANY_ID) REFERENCES Companies(id) ON DELETE CASCADE
);
```

#### **customers_vs_coupons** (Join Table)
```sql
CREATE TABLE customers_vs_coupons (
    CUSTOMER_ID INT NOT NULL,
    COUPON_ID INT NOT NULL,
    PRIMARY KEY (CUSTOMER_ID, COUPON_ID),
    FOREIGN KEY (CUSTOMER_ID) REFERENCES Customers(ID) ON DELETE CASCADE,
    FOREIGN KEY (COUPON_ID) REFERENCES Coupons(ID) ON DELETE CASCADE
);
```

## 🔧 Configuration

### **application.properties**
```properties
# Server
server.port=9096

# Database
spring.datasource.url=jdbc:mysql://db:3306/couponsystem?createDatabaseIfNotExist=true
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.properties.hibernate.format_sql=true
```

### **Environment Variables**
Set in `.env` file (root directory):
```env
DB_USER=root
DB_PASSWORD=your_secure_password
DB_ROOT_PASSWORD=your_root_password
```

## 🚀 Running the Backend

### **With Docker (Recommended)**
```bash
# From project root
docker-compose up -d backend
```

### **Locally (Development)**
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

### **Build JAR**
```bash
mvn clean package
java -jar target/coupons-system-0.0.1-SNAPSHOT.jar
```

## 📡 API Endpoints

### **Admin Endpoints**
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/admin/loginAdmin/{email}/{password}` | Admin login |
| GET | `/admin/getAllCompanies` | Get all companies |
| GET | `/admin/getAllCustomers` | Get all customers |
| POST | `/admin/addCompany` | Add new company |
| POST | `/admin/addCustomer` | Add new customer |
| DELETE | `/admin/deleteCompany/{id}` | Delete company |
| DELETE | `/admin/deleteCustomer/{id}` | Delete customer |

### **Company Endpoints**
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/company/loginCompany/{email}/{password}` | Company login |
| GET | `/company/getCompanyCoupons` | Get company's coupons |
| GET | `/company/getCompanyDetails` | Get company details |
| POST | `/company/addCoupon` | Create new coupon |
| PUT | `/company/updateCoupon` | Update coupon |
| DELETE | `/company/deleteCoupon/{id}` | Delete coupon |

### **Customer Endpoints**
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/customer/loginCustomer/{email}/{password}` | Customer login |
| GET | `/customer/getAllCoupons` | Browse all coupons |
| GET | `/customer/getCustomerCoupons` | Get purchased coupons |
| GET | `/customer/getCustomerDetails` | Get customer details |
| POST | `/customer/addCouponPurchase` | Purchase a coupon |

## 🔐 CORS Configuration

All controllers have CORS enabled for Angular frontend:
```java
@CrossOrigin(origins = "http://localhost:4200")
```

## 🌱 Data Seeding

The `DataLoader` component automatically seeds the database on startup if empty:

- **8 Companies** (Apple, Samsung, Tesla, Starbucks, Amazon, CocaCola, Netflix, Spotify)
- **6 Customers** (Emma Stone, Tom Holland, Robert Downey, etc.)

## 🔍 Key Implementation Details

### **Spring Data JPA Query Derivation**
No manual SQL queries! Method names are automatically converted to queries:
```java
// Automatically generates: SELECT * FROM Coupons WHERE company_id = ?
Set<Coupon> findByCompanyId(int companyId);

// Automatically generates: SELECT * FROM Coupons WHERE price <= ? AND company_id = ?
Set<Coupon> findByPriceLessThanEqualAndCompanyId(double price, int companyId);
```

### **JSON Circular Reference Handling**
```java
// Company.java
@JsonManagedReference(value="company-coupon")
public Set<Coupon> getCoupons() { ... }

// Coupon.java
@JsonBackReference(value="company-coupon")
public Company getCompany() { ... }

@JsonIgnore
public Set<Customer> getCustomers() { ... }
```

### **Transaction Management**
```java
@Service
@Transactional
public class CompanyServicesimpl { ... }
```

## 🐛 Common Issues

### **Port 9096 already in use**
```bash
# Find process
lsof -i :9096
# Kill process
kill -9 <PID>
```

### **Database connection refused**
- Ensure MySQL container is running: `docker ps`
- Check `.env` credentials
- Verify `application.properties` datasource URL

### **Hibernate schema errors**
- Set `spring.jpa.hibernate.ddl-auto=create` to recreate schema
- Or manually drop database: `DROP DATABASE couponsystem;`

## 📝 Development Notes

- **Session Management**: Currently uses in-memory `companyId`/`customerId` (singleton service). For production, implement JWT or session-based auth.
- **Password Security**: Passwords are stored in plain text. Use BCrypt for production.
- **Error Handling**: Uses custom `MyException` and `RuntimeException`. Consider global exception handler.

## 🚀 Future Improvements

- [ ] JWT authentication
- [ ] BCrypt password hashing
- [ ] Global exception handler with `@ControllerAdvice`
- [ ] Request/Response DTOs
- [ ] Pagination for large datasets
- [ ] API documentation with Swagger/OpenAPI
- [ ] Unit tests with JUnit & Mockito
- [ ] Integration tests with TestContainers

---

**Backend Version:** 0.0.1-SNAPSHOT  
**Spring Boot Version:** 2.4.2  
**Java Version:** 11
