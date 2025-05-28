package com.bilal;

import java.util.Scanner;

public class LoginPage {
    public static boolean login(Scanner scanner) {
        int attempts = 3;

        while (attempts > 0) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            if (username.equals("admin") && password.equals("hello")) {
                System.out.println("Login Successful! Proceeding to Inventory System...");
                return true;
            } else {
                attempts--;
                System.out.println("Invalid credentials. " + attempts + " attempts left.");
            }
        }

        System.out.println("Too many attempts. Exiting...");
        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (login(scanner)) {
            Main.main(args);
        }
    }
}