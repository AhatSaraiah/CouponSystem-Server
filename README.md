# Coupon Management System

The Coupon Management System is designed to manage and distribute coupons efficiently. This project focuses on the server-side implementation using the Spring Boot framework, Hibernate, and JPA.

## Features

- **Spring Boot**: Provides a robust framework for building Java-based applications.
- **Hibernate**: Used for ORM (Object Relational Mapping) to interact with the database.
- **JPA**: Java Persistence API for managing relational data in applications.

## Prerequisites

- **Java 11**: Ensure you have Java SE Development Kit 11 installed.
- **Maven**: Apache Maven 3.6.3 or higher.
- **Eclipse IDE**: Eclipse IDE for Enterprise Java and Web Developers (Photon or later).

## Getting Started

### Importing the Project in Eclipse

1. **Open Eclipse** and select **_File > Import_**.
2. In the import wizard, choose **_Maven > Existing Maven Projects_**, then click **_Next_**.
3. **Select the project root directory** where the Coupon Management System project is located.
4. Click **_Finish_** to complete the import.

### Project Configuration

1. **Java Build Path**:
   - Select **_Project > Properties_**.
   - In **_Java Build Path_**, ensure that under the **_Libraries_** tab, **_Modulepath_** is set to **_JRE System Library (JavaSE-11)_**.

2. **Java Compiler**:
   - In **_Java Compiler_**, ensure that the **_Use compliance from execution environment 'JavaSE-11' on the 'Java Build Path'_** checkbox is selected.

### Running the Project

1. **Run Maven Build**:
   - Right-click the project in the Project Explorer or Package Explorer and choose **_Run As > Maven Build..._**.
   - In the **_Edit Configuration_** dialog, create a new configuration with the name `unpack`.
   - In the **_Goals_** field, enter `dependency:unpack`.
   - Click **_Run_** to run the goal. This will unpack the native libraries into `$USER_HOME/.arcgis`.

2. **Compile and Launch**:
   - Again, create a new run configuration with the name `run`.
   - In the **_Goals_** field, enter `compile exec:java`.
   - Click **_Run_** to run the goal. The application should compile and launch the JavaFX window.

## Project Structure

- **src/main/java**: Contains the main application code and packages.
- **src/main/resources**: Contains application properties and configuration files.
- **pom.xml**: Maven configuration file for managing dependencies and build instructions.

## Technologies Used

- **Spring Boot**: Framework for building Java applications.
- **Hibernate**: ORM framework for database operations.
- **JPA**: API for managing relational data.
- **Maven**: Build and dependency management tool.

## Contributing

1. Fork the repository.
2. Create a new branch (`git checkout -b feature/YourFeature`).
3. Commit your changes (`git commit -am 'Add new feature'`).
4. Push to the branch (`git push origin feature/YourFeature`).
5. Create a new Pull Request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact

For any questions or issues, please contact:

- Ahat Saraiah
- [Email](mailto:ahat.saraiah@gmail.com)
- [GitHub](https://github.com/AhatSaraiah)
