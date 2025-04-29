package com.retima.ui;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import com.retima.database.DBConnection;

public class AgentDetailsUI extends JFrame {
    public AgentDetailsUI(String agentName) {
        setTitle("تفاصيل الوكيل");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM sellers WHERE name = ?")) {

            stmt.setString(1, agentName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));

                panel.add(new JLabel("اسم الوكيل:"));
                panel.add(new JLabel(rs.getString("name")));

                panel.add(new JLabel("رقم الهاتف:"));
                panel.add(new JLabel(rs.getString("phone")));

                panel.add(new JLabel("العنوان:"));
                panel.add(new JLabel(rs.getString("address")));

                panel.add(new JLabel("أقل سعر:"));
                panel.add(new JLabel(String.valueOf(rs.getDouble("min_price")) + " $"));

                panel.add(new JLabel("أعلى سعر:"));
                panel.add(new JLabel(String.valueOf(rs.getDouble("max_price")) + " $"));

                panel.add(new JLabel("مدة الكراء:"));
                panel.add(new JLabel(rs.getString("rent_period")));

                panel.add(new JLabel("مرات التفاوض:"));
                panel.add(new JLabel(String.valueOf(rs.getInt("negotiation_count"))));

                JButton negotiateButton = new JButton("بدء التفاوض");
                negotiateButton.addActionListener(e -> {
                    new NegotiationUI(agentName).setVisible(true);
                    updateNegotiationCount(agentName);
                });

                add(panel, BorderLayout.CENTER);
                add(negotiateButton, BorderLayout.SOUTH);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateNegotiationCount(String agentName) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "UPDATE sellers SET negotiation_count = negotiation_count + 1 WHERE name = ?")) {

            stmt.setString(1, agentName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}