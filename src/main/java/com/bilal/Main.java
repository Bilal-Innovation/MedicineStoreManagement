package com.bilal;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static MedicineService service;
    private static Scanner scanner;

    public static void startApplication() {
        service = new MedicineService();
        scanner = new Scanner(System.in);

        // Test database connection
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("Connected to the database successfully!");
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            return;
        }

        // Login integration
        if (!LoginPage.login(scanner)) {
            return;
        }

        // Console-based menu
        while (true) {
            System.out.println("\nMedicine Store Management System");
            System.out.println("1. Add Medicine");
            System.out.println("2. Search Medicine by Name");
            System.out.println("3. Search Medicine by Formula");
            System.out.println("4. Update Medicine");
            System.out.println("5. Delete Medicine by Name");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            try {
                switch (choice) {
                    case 1: // Add Medicine
                        System.out.print("Enter name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter formula: ");
                        String formula = scanner.nextLine();
                        System.out.print("Enter price: ");
                        double price = scanner.nextDouble();
                        scanner.nextLine(); // Clear buffer
                        System.out.print("Enter type (Tablet/Syrup): ");
                        String type = scanner.nextLine();
                        System.out.print("Enter quantity: ");
                        int quantity = scanner.nextInt();
                        scanner.nextLine(); // Clear buffer

                        Medicine newMedicine = new Medicine(name, formula, price, type, quantity);
                        service.addMedicine(newMedicine);
                        System.out.println("Added: " + newMedicine);
                        break;

                    case 2: // Search by Name with Quantity Option
                        System.out.print("Enter name to search: ");
                        String searchName = scanner.nextLine();
                        List<Medicine> nameResults = service.searchMedicineByName(searchName);
                        if (nameResults.isEmpty()) {
                            System.out.println("No medicines found with name: " + searchName);
                        } else {
                            nameResults.forEach(System.out::println);
                            System.out.print("Adjust quantity? (Y/N): ");
                            String adjust = scanner.nextLine();
                            if (adjust.equalsIgnoreCase("Y")) {
                                Medicine selectedMedicine = nameResults.get(0);
                                System.out.print("Increment (1) or Decrement (-1): ");
                                int change = scanner.nextInt();
                                scanner.nextLine(); // Clear buffer
                                service.updateQuantity(selectedMedicine.getId(), change);
                                System.out.println("Updated: " + selectedMedicine);
                            }
                        }
                        break;

                    case 3: // Search by Formula with Quantity Option
                        System.out.print("Enter formula to search: ");
                        String searchFormula = scanner.nextLine();
                        List<Medicine> formulaResults = service.searchMedicineByFormula(searchFormula);
                        if (formulaResults.isEmpty()) {
                            System.out.println("No medicines found with formula: " + searchFormula);
                        } else {
                            for (int i = 0; i < formulaResults.size(); i++) {
                                System.out.println("[" + i + "] " + formulaResults.get(i));
                            }
                            System.out.print("Adjust quantity? (Y/N): ");
                            String adjust = scanner.nextLine();
                            if (adjust.equalsIgnoreCase("Y")) {
                                System.out.print("Select medicine by index (0 to " + (formulaResults.size() - 1) + "): ");
                                int index = scanner.nextInt();
                                scanner.nextLine(); // Clear buffer
                                if (index >= 0 && index < formulaResults.size()) {
                                    Medicine selectedMedicine = formulaResults.get(index);
                                    System.out.print("Increment (1) or Decrement (-1): ");
                                    int change = scanner.nextInt();
                                    scanner.nextLine(); // Clear buffer
                                    service.updateQuantity(selectedMedicine.getId(), change);
                                    System.out.println("Updated: " + selectedMedicine);
                                } else {
                                    System.out.println("Invalid index!");
                                }
                            }
                        }
                        break;

                    case 4: // Update Medicine by Name
                        System.out.print("Enter medicine name to update: ");
                        String updateName = scanner.nextLine();
                        Medicine medicineToUpdate = service.getMedicineByName(updateName);
                        if (medicineToUpdate == null) {
                            System.out.println("No medicine found with name: " + updateName);
                        } else {
                            System.out.println("Current record: " + medicineToUpdate);
                            System.out.print("Enter new price: ");
                            double newPrice = scanner.nextDouble();
                            scanner.nextLine(); // Clear buffer
                            System.out.print("Enter new formula: ");
                            String newFormula = scanner.nextLine();
                            System.out.print("Enter new quantity: ");
                            int newQuantity = scanner.nextInt();
                            scanner.nextLine(); // Clear buffer

                            Medicine updatedMedicine = new Medicine(
                                    medicineToUpdate.getName(),
                                    newFormula,
                                    newPrice,
                                    medicineToUpdate.getType(),
                                    newQuantity
                            );
                            updatedMedicine.setId(medicineToUpdate.getId()); // Preserve id
                            service.updateMedicine(updatedMedicine);
                            System.out.println("Updated: " + updatedMedicine);
                        }
                        break;

                    case 5: // Delete Medicine by Name
                        System.out.print("Enter name to delete: ");
                        String deleteName = scanner.nextLine();
                        service.deleteMedicineByName(deleteName);
                        System.out.println("Deleted medicine with name: " + deleteName);
                        break;

                    case 6: // Exit
                        System.out.println("Exiting...");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid choice! Try again.");
                }
            } catch (SQLException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        startApplication();
    }
}