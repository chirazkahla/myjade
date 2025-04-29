package com.retima;

import jade.core.Agent;
import jade.core.behaviours.*;

public class ParallelAgent extends Agent {
    @Override
    protected void setup() {
        System.out.println("Agent " + getLocalName() + " is starting.\n");

        // WHEN_ALL
        ParallelBehaviour whenAll = new ParallelBehaviour(ParallelBehaviour.WHEN_ALL);

        whenAll.addSubBehaviour(new OneShotBehaviour() {
            public void action() {
                System.out.println(" OneShot in WHEN_ALL is running...");
            }
        });

        whenAll.addSubBehaviour(new CyclicBehaviour() {
            private int count = 0;

            public void action() {
                System.out.println(" CyclicBehaviour in WHEN_ALL is running... Count: " + count);
                if (++count >= 3) {
                    System.out.println("Stopping CyclicBehaviour in WHEN_ALL after 3 iterations.");
                    myAgent.removeBehaviour(this);
                }
                block(400);
            }
        });

        whenAll.addSubBehaviour(new TickerTask(this, 400, 3));
        addBehaviour(whenAll);

        // WHEN_ANY
        ParallelBehaviour whenAny = new ParallelBehaviour(ParallelBehaviour.WHEN_ANY);

        whenAny.addSubBehaviour(new OneShotBehaviour() {
            public void action() {
                System.out.println("OneShot in WHEN_ANY is running...");
            }
        });

        whenAny.addSubBehaviour(new CyclicBehaviour() {
            private int count = 0;

            public void action() {
                System.out.println(" CyclicBehaviour in WHEN_ANY is running... Count: " + count);
                if (++count >= 2) {
                    System.out.println(" Stopping CyclicBehaviour in WHEN_ANY after 2 iterations.");
                    myAgent.removeBehaviour(this);
                }
                block(400);
            }
        });

        whenAny.addSubBehaviour(new TickerTask(this, 400, 2));
        addBehaviour(whenAny);

        // WHEN_N
        ParallelBehaviour whenN = new ParallelBehaviour(2);

        whenN.addSubBehaviour(new OneShotBehaviour() {
            public void action() {
                System.out.println("OneShot in WHEN_N is running...");
            }
        });

        whenN.addSubBehaviour(new CyclicBehaviour() {
            private int count = 0;

            public void action() {
                System.out.println("CyclicBehaviour in WHEN_N is running... Count: " + count);
                if (++count >= 2) {
                    System.out.println(" Stopping CyclicBehaviour in WHEN_N after 2 iterations.");
                    myAgent.removeBehaviour(this);
                }
                block(400);
            }
        });

        whenN.addSubBehaviour(new TickerTask(this, 400, 3));
        addBehaviour(whenN);
    }

    private static class TickerTask extends TickerBehaviour {
        private int tickCount = 0;
        private final int maxTicks;

        public TickerTask(Agent a, long period, int maxTicks) {
            super(a, period);
            this.maxTicks = maxTicks;
        }

        @Override
        protected void onTick() {
            tickCount++;
            System.out.println("[TickerTask] when N: " + tickCount);

            if (tickCount >= maxTicks) {
                System.out.println("[TickerTask] Reached max ticks. Stopping.");
                stop();
            }
        }
    }

    @Override
    protected void takeDown() {
        System.out.println("Agent " + getLocalName() + " is terminating.");
    }
}
