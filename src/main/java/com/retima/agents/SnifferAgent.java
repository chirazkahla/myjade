package com.retima.agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class SnifferAgent extends Agent {
    protected void setup() {
        System.out.println("وكيل المراقبة بدأ عمله");

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    System.out.println("تم استلام رسالة من " + msg.getSender().getName() + ": " + msg.getContent());
                } else {
                    block();
                }
            }
        });
    }
}
