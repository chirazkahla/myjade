package com.retima;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.Scanner;

public class Agent1 extends Agent {
    protected void setup() {
        System.out.println("Agent1 started. Waiting for user input...");
        addBehaviour(new NumberRequestBehaviour());
    }

    private class NumberRequestBehaviour extends OneShotBehaviour {
        public void action() {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("Enter a number: ");
                int number = scanner.nextInt();

                ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                msg.addReceiver(getAID("Agent2"));
                msg.setContent(String.valueOf(number));
                send(msg);

                // Agent2
                ACLMessage reply = blockingReceive();
                if (reply != null) {
                    if (reply.getContent().equals("EVEN")) {
                        System.out.println("Agent1: The number is even. Program ends.");
                        doDelete(); 
                        break;
                    } else {
                        System.out.println("Agent1: The number is odd. Please enter a new number.");
                    }
                }
            }
        }
    }
}
