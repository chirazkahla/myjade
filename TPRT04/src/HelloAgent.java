package com.retima;

import jade.core.Agent;

public class HelloAgent extends Agent {
    @Override
    protected void setup() {
        System.out.println("HelloAgent: I am alive!");
        // Add agent behavior or logic here
    }
}