# Coupon System — REST API

[![CI](https://github.com/AhatSaraiah/CouponSystem-Server/actions/workflows/ci.yml/badge.svg?branch=main)](https://github.com/AhatSaraiah/CouponSystem-Server/actions/workflows/ci.yml)
[![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk&logoColor=white)](https://openjdk.org)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-6DB33F?logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![License: MIT](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

Spring Boot API behind the Coupon Management System. Three client types share one data model:
administrators manage companies and customers, companies publish coupons, and customers buy them.
The Angular client lives in [CouponSystemWebsite](https://github.com/AhatSaraiah/CouponSystemWebsite).

## Endpoints

| Role | Method | Path | Purpose |
| --- | --- | --- | --- |
| Admin | GET | `/admin/loginAdmin/{email}/{password}` | Admin sign-in |
| Admin | GET | `/admin/getAllCompanies`, `/admin/getOneCompany/{id}` | Read companies |
| Admin | POST / PUT / DELETE | `/admin/addCompany`, `/admin/updateCompany`, `/admin/deleteCompany/{id}` | Manage companies |
| Admin | GET | `/admin/getAllCustomers`, `/admin/getOneCustomer/{id}` | Read customers |
| Admin | POST / PUT / DELETE | `/admin/addCustomer`, `/admin/updateCustomer`, `/admin/deleteCustomer/{id}` | Manage customers |
| Company | GET | `/company/loginCompany/{email}/{password}` | Company sign-in |
| Company | GET | `/company/getCompanyDetails` | Signed-in company |
| Company | GET | `/company/getCompanyCoupons`, `/company/getCompanyCoupons/{category}`, `/company/getCompanyCoupons/{maxPrice}` | Company coupons |
| Company | POST / PUT / DELETE | `/company/addCoupon`, `/company/updateCoupon`, `/company/deleteCoupon/{id}` | Manage coupons |
| Customer | GET | `/customer/loginCustomer/{email}/{password}` | Customer sign-in |
| Customer | GET | `/customer/getCustomerDetails` | Signed-in customer |
| Customer | GET | `/customer/getAllCoupons`, `/customer/getCustomerCoupons`, `/customer/getCustomerCoupons/{category}`, `/customer/getCustomerCoupons/{maxPrice}` | Browse and owned coupons |
| Customer | POST | `/customer/addCouponPurchase` | Buy a coupon |
| Ops | GET | `/actuator/health` | Liveness and readiness probe |

## Tech stack

| Layer | Choice |
| --- | --- |
| Framework | Spring Boot 3.5 on Java 17 |
| Persistence | Spring Data JPA / Hibernate 6, MySQL 8 in production, H2 in tests and the demo profile |
| Web | Spring MVC, Jackson |
| Ops | Spring Boot Actuator health probes, Docker multi-stage image, GitHub Actions |
| Tests | JUnit 5, MockMvc integration tests |

## Project layout

```
src/main/java/com/coupons_system/
├── config/          # WebConfig: CORS from app.cors.allowed-origins
├── controller/      # Admin, Company, Customer and shared Client controllers
├── job/             # CouponExpirationDailyJob: expires coupons daily
├── login/           # ClientType and LoginManager
├── model/           # Company, Customer, Coupon, Category
├── repositories/    # Spring Data repositories
├── service/         # Service interfaces and implementations per role
└── utils/           # MyException and the DataLoader that seeds sample data
```

## Run it

### Demo profile (no database needed)

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=demo
# API on http://localhost:9096, seeded with sample companies and customers
```

### With MySQL

```bash
docker compose up --build          # API + MySQL 8
```

Or point the app at your own database:

```bash
export SPRING_DATASOURCE_URL="jdbc:mysql://localhost:3306/coupons?allowPublicKeyRetrieval=true&useSSL=false"
export SPRING_DATASOURCE_USERNAME=coupons
export SPRING_DATASOURCE_PASSWORD=coupons
./mvnw spring-boot:run
```

The admin account defaults to `admin@admin.com` / `admin`; every seeded company and customer
password is visible in `DataLoader`.

## Configuration

| Variable | Default | Purpose |
| --- | --- | --- |
| `SPRING_DATASOURCE_URL` | none (required outside the demo profile) | JDBC URL |
| `SPRING_DATASOURCE_USERNAME` / `SPRING_DATASOURCE_PASSWORD` | none | Database credentials |
| `PORT` | `9096` | HTTP port; hosting platforms inject it |
| `APP_CORS_ALLOWED_ORIGINS` | `http://localhost:4200` | Comma-separated origins allowed to call the API |
| `APP_ADMIN_EMAIL` / `APP_ADMIN_PASSWORD` | `admin@admin.com` / `admin` | Administrator account |
| `SPRING_PROFILES_ACTIVE` | none | Set to `demo` for the in-memory profile |
| `SPRING_JPA_SHOW_SQL` | `false` | Log SQL statements |

## Deployment

The repository ships everything needed to run the API as a container:

- **Docker** — `Dockerfile` builds a Java 17 image, runs as a non-root user and exposes a
  `/actuator/health` healthcheck.
- **Render** — `render.yaml` is a blueprint: New > Blueprint, pick this repository, and set
  `APP_ADMIN_PASSWORD` plus `APP_CORS_ALLOWED_ORIGINS` (the frontend URL) when prompted. It starts on
  the `demo` profile, so it runs without a database.
- **Any container host** — build the image and provide the environment variables above; the app reads
  `PORT` automatically.

Point the Angular client at the deployed API by setting `apiBaseUrl` in
`src/environments/environment.prod.ts`.

## Tests

```bash
./mvnw test
```

Seven tests cover the application context, the admin and company login endpoints, the CORS response
for the Angular dev server, the actuator health probe, and the guarantee that passwords are never
serialized into API responses.

## Known limitations

- **Credentials in the URL path.** Sign-in is `GET /{role}/login.../{email}/{password}`, so passwords
  travel in the URL and end up in access logs. Moving to `POST` with a JSON body and a token is the
  next change, and the Angular client is already isolated behind one method per role.
- **Plain-text passwords.** Passwords are stored as-is; BCrypt hashing is the planned fix.
- **No authentication token.** The API trusts the caller once a login returns `true`; every endpoint is
  reachable without a session.

## License

[MIT](LICENSE) © Ahat Saraiah
