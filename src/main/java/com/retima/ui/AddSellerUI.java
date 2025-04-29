package com.retima.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.retima.database.DBConnection;

public class AddSellerUI extends JFrame {
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField addressField;
    private JTextField minPriceField;
    private JTextField maxPriceField;
    private JTextField rentPeriodField;
    private JButton submitButton;

    public AddSellerUI() {
        setTitle("إضافة وكيل بائع");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));

        panel.add(new JLabel("اسم الوكيل:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("رقم الهاتف:"));
        phoneField = new JTextField();
        panel.add(phoneField);

        panel.add(new JLabel("العنوان:"));
        addressField = new JTextField();
        panel.add(addressField);

        panel.add(new JLabel("أقل سعر للمنزل ($):"));
        minPriceField = new JTextField();
        panel.add(minPriceField);

        panel.add(new JLabel("أعلى سعر للمنزل ($):"));
        maxPriceField = new JTextField();
        panel.add(maxPriceField);

        panel.add(new JLabel("مدة الكراء:"));
        rentPeriodField = new JTextField();
        panel.add(rentPeriodField);

        submitButton = new JButton("حفظ البيانات");
        panel.add(submitButton);

        add(panel);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sellerName = nameField.getText();
                String phone = phoneField.getText();
                String address = addressField.getText();
                String minPriceStr = minPriceField.getText();
                String maxPriceStr = maxPriceField.getText();
                String rentPeriod = rentPeriodField.getText();

                if (sellerName.isEmpty() || phone.isEmpty() || address.isEmpty() ||
                        minPriceStr.isEmpty() || maxPriceStr.isEmpty() || rentPeriod.isEmpty()) {
                    JOptionPane.showMessageDialog(AddSellerUI.this, "الرجاء ملء جميع الحقول!");
                    return;
                }

                try {
                    double minPrice = Double.parseDouble(minPriceStr);
                    double maxPrice = Double.parseDouble(maxPriceStr);

                    saveSellerToDatabase(sellerName, phone, address, minPrice, maxPrice, rentPeriod);

                    JOptionPane.showMessageDialog(AddSellerUI.this, "تمت إضافة الوكيل بنجاح!");
                    dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(AddSellerUI.this, "الرجاء إدخال قيم صحيحة للأسعار!");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(AddSellerUI.this, "حدث خطأ أثناء حفظ البيانات!");
                }
            }
        });
    }

    private void saveSellerToDatabase(String name, String phone, String address,
            double minPrice, double maxPrice, String rentPeriod)
            throws SQLException {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO sellers (name, phone, address, min_price, max_price, rent_period, negotiation_count) "
                                +
                                "VALUES (?, ?, ?, ?, ?, ?, 0)")) {

            stmt.setString(1, name);
            stmt.setString(2, phone);
            stmt.setString(3, address);
            stmt.setDouble(4, minPrice);
            stmt.setDouble(5, maxPrice);
            stmt.setString(6, rentPeriod);

            stmt.executeUpdate();
        }
    }
}