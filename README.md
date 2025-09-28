📚 Library Management System (LMS)
A robust and secure web application built with Spring Boot and Thymeleaf for managing a library's core operations, including books, members, loans, and user authentication.

✨ Features
Book Management:

Add, View, Edit, and Delete book records.

Search and filter books by various criteria.

Member Management:

Register, View, Update, and Delete library member profiles.

Loan Tracking:

Create new loan records (book checkout).

Track loan status (due date, return).

User Authentication & Authorization:

Secure login and registration using Spring Security.

Role-based access control (Admin/User separation handled by SecurityConfig).

Web Interface:

Responsive and dynamic UI built with Thymeleaf templates.

💻 Technology Stack
This project is built using industry-standard technologies for enterprise Java development:

Category	Technology	Description
Backend Framework	Spring Boot	Simplifies configuration and deployment of the application.
Persistence	Spring Data JPA & Hibernate	Handles database operations and object-relational mapping (ORM).
Database	H2 Database (or configured via application.properties)	Default in-memory database for quick setup and testing.
Security	Spring Security	Provides robust authentication and authorization.
Frontend	Thymeleaf	Server-side templating engine for rendering HTML views.
Build Tool	Maven	Dependency management and build automation.
Language	Java	Core programming language.

Export to Sheets
⚙️ Prerequisites
To run this project locally, you need the following installed:

Java Development Kit (JDK) 17+

Maven 3.6+

(Optional) An Integrated Development Environment (IDE) like IntelliJ IDEA or Eclipse.

🚀 Installation and Setup
Follow these steps to get your local environment set up.

1. Clone the Repository
Bash

git clone https://github.com/KailasVS666/Library-Management-System-LMS.git
cd Library-Management-System-LMS
2. Configure the Database
The application is currently configured to use an in-memory database (H2) for simplicity, as seen in src/main/resources/application.properties.

To use an external database (e.g., MySQL or PostgreSQL), update the properties file:

Properties

# Example for MySQL connection
spring.datasource.url=jdbc:mysql://localhost:3306/lms_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update # Use 'create' for the first run
3. Build and Run the Application
Navigate to the project's root directory and use the Maven wrapper:

Bash

# Build the project (compiles and packages)
./mvnw clean package

# Run the application
java -jar target/library-management-system-0.0.1-SNAPSHOT.jar
Alternatively, you can run directly from your IDE.

4. Access the Application
The application will typically start on port 8080.

URL: http://localhost:8080/

🔒 Default Credentials
The DataLoader.java file likely initializes some default roles and/or an admin user on startup to ensure you can log in immediately.

Role	Username	Password
Admin	[Check DataLoader.java or project documentation]	[Check DataLoader.java or project documentation]
User/Member	[Check DataLoader.java or project documentation]	[Check DataLoader.java or project documentation]

Export to Sheets
If default credentials are not available, use the /register page to create a new user.

🤝 Contributing
(Optional: Add instructions here if you plan to accept contributions.)

📄 License
This project is licensed under the MIT License - see the LICENSE.md file for details.
