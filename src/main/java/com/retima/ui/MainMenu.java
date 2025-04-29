package com.retima.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
    private JButton addSellerButton;
    private JButton addTenantButton;

    public MainMenu() {
        setTitle("نظام كراء المنازل");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        addSellerButton = new JButton("إضافة وكيل");
        addTenantButton = new JButton("إضافة مستأجر");

        addSellerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // فتح نافذة إضافة الوكيل
                new AddSellerUI().setVisible(true);
            }
        });

        addTenantButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // فتح نافذة إضافة المستأجر
                new AddTenantUI().setVisible(true);
            }
        });

        JPanel panel = new JPanel();
        panel.add(addSellerButton);
        panel.add(addTenantButton);

        add(panel);
    }
}
