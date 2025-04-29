package com.retima;
import java.util.Scanner;
import jade.core.Agent;
import jade.core.behaviours.*;

public class MyAgent extends Agent {
    private int cyclicCounter = 0; 

    @Override
    protected void setup() {
        
        addBehaviour(new OneShotBehaviour() {
            public void action() {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter the temperature: ");

                if (scanner.hasNextDouble()) {
                    double temperature = scanner.nextDouble();

                    if (temperature < 0) {
                        System.out.println("Low temperature");
                    } else if (temperature <= 35) {
                        System.out.println("Moderate temperature");
                    } else if (temperature > 50) {
                        System.out.println("High temperature");
                    } else {
                        System.out.println("Temperature in an unclassified range.");
                    }
                } else {
                    System.out.println("Invalid input! Please enter a numeric temperature.");
                }

                scanner.close();
            }
        });

 
        addBehaviour(new CyclicBehaviour() {
            public void action() {
                System.out.println("CyclicBehaviour is running... Count: " + cyclicCounter);
                cyclicCounter++;

                if (cyclicCounter >= 5) {
                    System.out.println("Stopping CyclicBehaviour.");
                    removeBehaviour(this); 
                }

                block(2000); 
            }
        });

        
        SequentialBehaviour seqBehaviour = new SequentialBehaviour();
        for (int i = 0; i < 7; i++) {
            seqBehaviour.addSubBehaviour(new OneShotBehaviour() {
                public void action() {
                    System.out.println("Executing step in SequentialBehaviour...");
                }
            });
        }
        addBehaviour(seqBehaviour);

        
        ParallelBehaviour parallelBehaviour = new ParallelBehaviour(ParallelBehaviour.WHEN_ALL);
        parallelBehaviour.addSubBehaviour(new OneShotBehaviour() {
            public void action() {
                System.out.println("ParallelBehaviour instance running...");
            }
        });
        addBehaviour(parallelBehaviour);
    }
}