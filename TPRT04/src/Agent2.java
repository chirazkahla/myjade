import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class Agent2 extends Agent {
    protected void setup() {
        addBehaviour(new CyclicBehaviour() {
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    int number = Integer.parseInt(msg.getContent());
                    ACLMessage reply = msg.createReply();

                    if (number % 2 == 0) {
                        System.out.println("Agent2: The number " + number + " is even.");
                        reply.setContent("EVEN");
                    } else {
                        System.out.println("Agent2: The number " + number + " is odd.");
                        reply.setContent("ODD");
                    }

                    send(reply);
                } else {
                    block();
                }
            }
        });
    }
}
