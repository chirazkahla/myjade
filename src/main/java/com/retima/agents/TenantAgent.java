package com.retima.agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class TenantAgent extends Agent {
    protected void setup() {
        System.out.println("وكيل المستأجر بدأ عمله");

        // انتظار عرض المنزل من الوكيل البائع
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage offer = receive();
                if (offer != null && offer.getConversationId().equals("house-rent")) {
                    System.out.println("تم استلام العرض: " + offer.getContent());

                    // اتخاذ القرار: قبول أو رفض العرض
                    ACLMessage reply = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                    if (offer.getContent().contains("1000$")) {
                        reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL); // قبول العرض
                    } else {
                        reply.setPerformative(ACLMessage.REJECT_PROPOSAL); // رفض العرض
                    }
                    reply.addReceiver(offer.getSender());
                    send(reply);
                } else {
                    block();
                }
            }
        });
    }
}
