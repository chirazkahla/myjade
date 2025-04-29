package com.retima.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddTenantUI extends JFrame {
    private JTextField nameField;
    private JTextField budgetField;
    private JButton submitButton;

    public AddTenantUI() {
        setTitle("Add Buyer");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        panel.add(new JLabel("Buyer Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Budget ($):"));
        budgetField = new JTextField();
        panel.add(budgetField);

        submitButton = new JButton("Submit");
        panel.add(submitButton);

        add(panel);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buyerName = nameField.getText();
                String budget = budgetField.getText();

                if (buyerName.isEmpty() || budget.isEmpty()) {
                    JOptionPane.showMessageDialog(AddTenantUI.this, "Please fill in all fields!");
                    return;
                }

                // هنا ممكن تخزن البيانات في قاعدة البيانات أو ترسلها للوكيل
                JOptionPane.showMessageDialog(AddTenantUI.this, "Buyer added successfully!");
                dispose();
            }
        });
    }
}
