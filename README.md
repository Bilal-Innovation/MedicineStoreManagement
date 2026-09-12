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

# LoginPage
<img width="494" height="330" alt="Login_Page" src="https://github.com/user-attachments/assets/e1fad527-bcff-4d4d-8ff9-629e43926847" />

# Dashboard
<img width="866" height="414" alt="Dashboard_Page" src="https://github.com/user-attachments/assets/358abcd7-b858-4c4d-b983-1aa550866bb8" />

# Add Medicine 
<img width="619" height="416" alt="Add_Medicine Page" src="https://github.com/user-attachments/assets/c3a950a2-3e73-4f54-91c1-e0968dfe553d" />

# Quantity Adjustment
<img width="443" height="315" alt="Quantity Adujustment Page" src="https://github.com/user-attachments/assets/fa11499a-7e50-48ef-ac90-90863c9a5080" />

# Update Medicine 
<img width="443" height="315" alt="Quantity Adujustment Page" src="https://github.com/user-attachments/assets/45507208-cc23-4375-8ea5-1cd7f119c86b" />

# Search Medicine 
<img width="774" height="481" alt="Search_Medicine Page" src="https://github.com/user-attachments/assets/f908deac-858f-41b2-a5f3-f77edbd451ee" />

# Delete Medicine 
<img width="727" height="416" alt="Delete_Medicine Page" src="https://github.com/user-attachments/assets/cf1cf177-2382-45e1-bb9a-dbd639774350" />






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
