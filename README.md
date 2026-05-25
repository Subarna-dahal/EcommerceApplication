# Ecommerce Application 🛒

A Spring Boot based E-commerce backend application with user authentication, role-based access control, cart management, and order processing.

## 🚀 Features
- User authentication & authorization (Spring Security, JWT)
- Role-based access (Admin vs User)
- Product management (CRUD)
- Cart management (add, update, remove, checkout)
- Order management (create, update, delete, status updates)
- Global exception handling
- Validation with `jakarta.validation`

## 🛠 Tech Stack
- **Backend:** Java, Spring Boot, Spring Security
- **Database:** MySQL (JPA/Hibernate)
- **Build Tool:** Maven
- **Testing:** JUnit 5, Mockito
- **Version Control:** Git & GitHub

---

## 📂 Project Structure
src/main/java/com/example/EcommerceApplication
│── Controller        # REST controllers (OrderController, CartController, ProductController)
│── Entity            # JPA entities (UserEntity, ProductEntity, CartEntity, OrderEntity)
│── Repository        # Spring Data JPA repositories
│── Services          # Business logic (CartServices, OrderServices, UserServices)
│── Exception         # Custom exceptions & global handler
│── DataTransferObject# DTOs (StatusUpdateRequest, etc.)

## ⚙️ Setup Instructions
1. Clone the repository:
   git clone https://github.com/Subarna-Dahal/ecommerce-application.git
2. cd ecommerce-application
3. Configure your database in application.properties:
    spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
    spring.datasource.username=root
    spring.datasource.password=yourpassword
    spring.jpa.hibernate.ddl-auto=update
4.Run the application:
mvn spring-boot:run
