package com.retima.agents;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

public class AgentManager {
    public static void initializeJADE() {
        Runtime rt = Runtime.instance();
        ProfileImpl profile = new ProfileImpl();
        profile.setParameter(ProfileImpl.GUI, "true"); // ضروري لكي تظهر واجهة JADE

        AgentContainer container = rt.createMainContainer(profile);

        try {
            AgentController sellerAgent = container.createNewAgent("seller", SellerAgent.class.getName(), null);
            AgentController tenantAgent = container.createNewAgent("tenant", TenantAgent.class.getName(), null);
            AgentController snifferAgent = container.createNewAgent("sniffer", "jade.tools.sniffer.Sniffer", null); // نستعمل
            snifferAgent.putO2AObject("ADD seller tenant", AgentController.ASYNC);
                                                                                                         // sniffer
                                                                                                                    // جاهز

            sellerAgent.start();
            tenantAgent.start();
            snifferAgent.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
