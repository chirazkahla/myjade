package com.retima.ui;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import com.retima.database.DBConnection;

public class NegotiationResultUI extends JFrame {
    public NegotiationResultUI(String sellerName, String tenantName, double minPrice, double maxPrice) {
        setTitle("نتيجة التفاوض");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT min_price, max_price FROM sellers WHERE name = ?")) {

            stmt.setString(1, sellerName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                double sellerMin = rs.getDouble("min_price");
                double sellerMax = rs.getDouble("max_price");

                boolean success = (maxPrice >= sellerMin) && (minPrice <= sellerMax);

                JLabel resultLabel = new JLabel(success ? "تم الاتفاق على الصفقة!" : "لم يتم الاتفاق");
                resultLabel.setFont(new Font("Arial", Font.BOLD, 20));
                resultLabel.setForeground(success ? Color.GREEN : Color.RED);

                JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
                panel.add(resultLabel);
                panel.add(new JLabel("اسم البائع: " + sellerName));
                panel.add(new JLabel("اسم المستأجر: " + tenantName));
                panel.add(new JLabel("نطاق السعر المقترح: " + minPrice + " - " + maxPrice + " $"));
                panel.add(new JLabel("نطاق سعر البائع: " + sellerMin + " - " + sellerMax + " $"));

                JButton okButton = new JButton("موافق");
                okButton.addActionListener(e -> {
                    saveTenantData(tenantName, minPrice, maxPrice);
                    if (success) {
                        deleteSeller(sellerName);
                    }
                    dispose();
                });

                add(panel, BorderLayout.CENTER);
                add(okButton, BorderLayout.SOUTH);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveTenantData(String name, double minPrice, double maxPrice) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO tenants (name, min_price, max_price) VALUES (?, ?, ?)")) {

            stmt.setString(1, name);
            stmt.setDouble(2, minPrice);
            stmt.setDouble(3, maxPrice);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteSeller(String name) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "DELETE FROM sellers WHERE name = ?")) {

            stmt.setString(1, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}