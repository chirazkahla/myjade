package com.retima.ui;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import com.retima.database.DBConnection;

public class AgentsListUI extends JFrame {
    private JTable agentsTable;

    public AgentsListUI() {
        setTitle("قائمة الوكلاء");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columns = { "اسم الوكيل", "أقل سعر", "أعلى سعر", "مدة الكراء", "مرات التفاوض" };
        Object[][] data = getAgentsData();

        agentsTable = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(agentsTable);

        JButton selectButton = new JButton("اختيار للتفاوض");
        selectButton.addActionListener(e -> {
            int selectedRow = agentsTable.getSelectedRow();
            if (selectedRow >= 0) {
                new AgentDetailsUI((String) data[selectedRow][0]).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "الرجاء اختيار وكيل");
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(selectButton, BorderLayout.SOUTH);

        add(panel);
    }

    private Object[][] getAgentsData() {
        // استرجاع البيانات من قاعدة البيانات
        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM sellers")) {

            // حساب عدد الصفوف
            rs.last();
            int rowCount = rs.getRow();
            rs.beforeFirst();

            Object[][] data = new Object[rowCount][5];
            int i = 0;

            while (rs.next()) {
                data[i][0] = rs.getString("name");
                data[i][1] = rs.getDouble("min_price");
                data[i][2] = rs.getDouble("max_price");
                data[i][3] = rs.getString("rent_period");
                data[i][4] = rs.getInt("negotiation_count");
                i++;
            }
            return data;
        } catch (SQLException e) {
            e.printStackTrace();
            return new Object[0][0];
        }
    }
}