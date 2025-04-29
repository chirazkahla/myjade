package com.retima;

import com.retima.agents.AgentManager;
import com.retima.ui.AddSellerUI;
import com.retima.ui.AddTenantUI; // اذا واجهة المستأجر عندك اسمها هكذا
import com.retima.ui.AgentsListUI;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // 1. تشغيل JADE مع الواجهة
        AgentManager.initializeJADE();

        // 2. إظهار الواجهات في نفس الوقت
        SwingUtilities.invokeLater(() -> {
            new AddSellerUI();
            new AddTenantUI();
             new AgentsListUI().setVisible(true);
        });
        
    }
}
