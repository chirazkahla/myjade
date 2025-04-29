package com.retima;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class SenderAgent extends Agent {
    private int number = 1; // يبدأ بالعدد 1

    protected void setup() {
        System.out.println("SenderAgent " + getAID().getName() + " is ready.");

        addBehaviour(new CyclicBehaviour(this) {
            public void action() {
                // إرسال العدد إلى ReceiverAgent
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.addReceiver(new jade.core.AID("ReceiverAgent", jade.core.AID.ISLOCALNAME));
                msg.setContent(String.valueOf(number));
                send(msg);
                System.out.println("Sent number: " + number);

                // انتظار الرد
                MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
                ACLMessage reply = receive(mt);
                if (reply != null) {
                    String content = reply.getContent();
                    if (content.equals("زوجي")) {
                        System.out.println("Received: زوجي. Stopping...");
                        doDelete(); // يتوقف العميل
                    } else if (content.equals("ليس زوجي")) {
                        System.out.println("Received: ليس زوجي. Trying next number...");
                        number++; // يجرب العدد التالي
                    }
                } else {
                    block();
                }
            }
        });
    }

    protected void takeDown() {
        System.out.println("SenderAgent " + getAID().getName() + " is terminating.");
    }
}