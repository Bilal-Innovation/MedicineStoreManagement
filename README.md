# Medicine Store Management System

Welcome to the **Medicine Store Management System**, a JavaFX-based application designed to streamline medicine inventory management with a strong emphasis on Object-Oriented Programming (OOP) concepts. This project allows users to add, search, update, and delete medicine records using a user-friendly interface.

## Features
- **Add Medicine**: Input new medicine details (name, formula, price, type, quantity).
- **Search Medicine**: Search by name or formula with quantity adjustment options.
- **Update Medicine**: Modify existing medicine records.
- **Delete Medicine**: Remove medicine entries from the database.
- **User Authentication**: Secure login system.
- **Database Integration**: Stores data using SQL (MySQL).

## Project Screenshots

![Login Page](Pictures%20of%20Project/Login_Page.png)
![Dashboard](Pictures%20of%20Project/Dashboard_Page.png)


## Technologies Used
- **JavaFX**: For the graphical user interface.
- **Java**: Core programming language with OOP focus.
- **SQL**: For database management.
- **Maven**: For project dependency management.

## OOP Concepts Demonstrated
This project showcases key OOP principles:
- **Encapsulation**: `Medicine` class encapsulates data (name, formula, price, etc.) with private fields and public getters/setters.
- **Inheritance**: Extends JavaFX classes for UI components (e.g., `Controller` classes).
- **Polymorphism**: Method overriding in controllers (e.g., `handleBack` across different controllers).
- **Abstraction**: `MedicineService` abstracts database operations, hiding implementation details.

## Installation
1. Clone the repository:
   ```
   git clone <your-repo-url>
   ```
2. Ensure MySQL is installed and running.
3. Update `DatabaseConnection.java` with your MySQL credentials.
4. Run `mvn clean install` to build the project.
5. Execute the application using `mvn javafx:run` or via your IDE.

## Usage
- Log in with default credentials (username: `admin`, password: `hello`).
- Navigate the dashboard to manage medicine records.
- Use the search feature to find and adjust quantities.
- Save changes or delete records as needed.

## Contributing
1. Fork the repository.
2. Create a new branch: `git checkout -b feature-name`.
3. Commit changes: `git commit -m "Description"`.
4. Push to the branch: `git push origin feature-name`.
5. Submit a pull request.

## Team
- **Muhammad Bilal**: Project Lead
- **Teacher**: khanbehram66@gmail.com
- **Abbas Hassan**: AbbasUET

## Acknowledgments
- Thanks to to Sir Behram Khan for guidance through my Journey.
- Inspired by open-source JavaFX and SQL tutorials.
