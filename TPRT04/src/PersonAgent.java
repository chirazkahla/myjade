package com.retima;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import java.sql.*;

public class PersonAgent extends Agent {
    private String name;
    private int age;
    private String email;

    protected void setup() {
        Object[] args = getArguments();
        if (args != null && args.length == 3) {
            name = (String) args[0];
            age = Integer.parseInt((String) args[1]);
            email = (String) args[2];
        }

        addBehaviour(new SavePersonBehaviour());
    }

    private class SavePersonBehaviour extends OneShotBehaviour {
        public void action() {
            saveToDatabase(name, age, email);
            System.out.println(" Person data processed by agent: " + getLocalName());
        }
    }

    private void saveToDatabase(String name, int age, String email) {
        String url = "jdbc:mysql://localhost:3306/agents_db";
        String user = "root";
        String password = "chiraz2003"; // ⚠️ تأكد من أمان كلمة السر في المشاريع الحقيقية

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            // 🛠️ إنشاء الجدول إذا لم يكن موجودًا
            String createTableSQL = "CREATE TABLE IF NOT EXISTS persons (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(100), " +
                    "age INT, " +
                    "email VARCHAR(100) UNIQUE)";

            connection.createStatement().execute(createTableSQL);

            // 🔍 التحقق من وجود البريد مسبقًا
            String checkSQL = "SELECT COUNT(*) FROM persons WHERE email = ?";
            try (PreparedStatement checkStmt = connection.prepareStatement(checkSQL)) {
                checkStmt.setString(1, email);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println(" Email already exists, skipping insert for: " + email);
                    return;
                }
            }

            // 📥 إدخال البيانات
            String insertSQL = "INSERT INTO persons (name, age, email) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
                pstmt.setString(1, name);
                pstmt.setInt(2, age);
                pstmt.setString(3, email);

                int rows = pstmt.executeUpdate();
                if (rows > 0) {
                    System.out.println(" Data inserted successfully for: " + name);
                }
            }
        } catch (SQLException e) {
            System.err.println(" Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
