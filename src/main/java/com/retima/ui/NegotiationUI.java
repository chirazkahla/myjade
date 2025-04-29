package com.retima.ui;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import com.retima.database.DBConnection;

public class NegotiationUI extends JFrame {
    public NegotiationUI(String sellerName) {
        setTitle("عملية التفاوض");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        // معلومات الوكيل البائع
        panel.add(new JLabel("اسم الوكيل البائع:"));
        panel.add(new JLabel(sellerName));

        // معلومات المستأجر
        panel.add(new JLabel("اسم المستأجر:"));
        JTextField tenantName = new JTextField();
        panel.add(tenantName);

        panel.add(new JLabel("أقل سعر يمكن دفعه:"));
        JTextField minPrice = new JTextField();
        panel.add(minPrice);

        panel.add(new JLabel("أعلى سعر يمكن دفعه:"));
        JTextField maxPrice = new JTextField();
        panel.add(maxPrice);

        JButton submitButton = new JButton("إرسال العرض");
        submitButton.addActionListener(e -> {
            // حفظ معلومات المستأجر وعرض نتيجة التفاوض
            new NegotiationResultUI(sellerName, tenantName.getText(),
                    Double.parseDouble(minPrice.getText()),
                    Double.parseDouble(maxPrice.getText())).setVisible(true);
            dispose();
        });

        add(panel, BorderLayout.CENTER);
        add(submitButton, BorderLayout.SOUTH);
    }
}