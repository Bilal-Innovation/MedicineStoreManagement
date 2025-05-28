package com.bilal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicineService {
    public void addMedicine(Medicine medicine) throws SQLException {
        String sql = "INSERT INTO medicines (name, formula, price, type, quantity) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, medicine.getName());
            pstmt.setString(2, medicine.getFormula());
            pstmt.setDouble(3, medicine.getPrice());
            pstmt.setString(4, medicine.getType());
            pstmt.setInt(5, medicine.getQuantity());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                medicine.setId(rs.getInt(1)); // Set auto-incremented id
            }
        }
    }

    public List<Medicine> searchMedicineByName(String name) throws SQLException {
        List<Medicine> medicines = new ArrayList<>();
        String sql = "SELECT * FROM medicines WHERE name LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + name + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Medicine m = new Medicine(
                        rs.getString("name"),
                        rs.getString("formula"),
                        rs.getDouble("price"),
                        rs.getString("type"),
                        rs.getInt("quantity")
                );
                m.setId(rs.getInt("id")); // Set id from database
                medicines.add(m);
            }
        }
        return medicines;
    }

    public List<Medicine> searchMedicineByFormula(String formula) throws SQLException {
        List<Medicine> medicines = new ArrayList<>();
        String sql = "SELECT * FROM medicines WHERE formula LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + formula + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Medicine m = new Medicine(
                        rs.getString("name"),
                        rs.getString("formula"),
                        rs.getDouble("price"),
                        rs.getString("type"),
                        rs.getInt("quantity")
                );
                m.setId(rs.getInt("id")); // Set id from database
                medicines.add(m);
            }
        }
        return medicines;
    }

    public Medicine getMedicineByName(String name) throws SQLException {
        String sql = "SELECT * FROM medicines WHERE name = ? LIMIT 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Medicine m = new Medicine(
                        rs.getString("name"),
                        rs.getString("formula"),
                        rs.getDouble("price"),
                        rs.getString("type"),
                        rs.getInt("quantity")
                );
                m.setId(rs.getInt("id")); // Set id from database
                return m;
            }
        }
        return null;
    }

    public void updateMedicine(Medicine medicine) throws SQLException {
        String sql = "UPDATE medicines SET formula = ?, price = ?, quantity = ?, type = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, medicine.getFormula());
            pstmt.setDouble(2, medicine.getPrice());
            pstmt.setInt(3, medicine.getQuantity());
            pstmt.setString(4, medicine.getType());
            pstmt.setInt(5, medicine.getId());
            pstmt.executeUpdate();
        }
    }

    public void updateQuantity(int id, int change) throws SQLException {
        String sql = "UPDATE medicines SET quantity = quantity + ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, change);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        }
    }

    public void deleteMedicineByName(String name) throws SQLException {
        String sql = "DELETE FROM medicines WHERE name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        }
    }
}