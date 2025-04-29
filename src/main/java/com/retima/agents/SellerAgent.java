package com.retima.agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class SellerAgent extends Agent {
    protected void setup() {
        System.out.println("وكيل البائع بدأ عمله");

        // إرسال عرض المستأجرين (عرض المنزل)
        ACLMessage offer = new ACLMessage(ACLMessage.PROPOSE);
        offer.addReceiver(new jade.core.AID("tenant", jade.core.AID.ISLOCALNAME));
        offer.setContent("عرض المنزل: السعر 1000$، مدة الكراء 12 شهرًا");
        offer.setConversationId("house-rent");
        send(offer);

        // انتظار رد المستأجر
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage reply = receive();
                if (reply != null) {
                    if (reply.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                        System.out.println("المستأجر وافق على العرض!");
                    } else if (reply.getPerformative() == ACLMessage.REJECT_PROPOSAL) {
                        System.out.println("المستأجر رفض العرض.");
                    }
                } else {
                    block();
                }
            }
        });
    }
}
