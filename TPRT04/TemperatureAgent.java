package com.retima;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class TemperatureAgent extends Agent {
    private double temperature;

    @Override
    protected void setup() {
        System.out.println("✅ Proxy " + getLocalName() + " started work");

        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                temperature = Math.random() * 50 - 10;
                checkTemperature();
            }
        });
    }

    private void checkTemperature() {
        if (temperature < 0) {
            System.out.println("The weather is frozen! Temperature: " + temperature + "°C");
        } else if (temperature <= 20) {
            System.out.println("The weather is cold! Temperature: " + temperature + "°C");
        } else if (temperature <= 30) {
            System.out.println("Moderate weather! Temperature: " + temperature + "°C");
        } else {
            System.out.println("The weather is very hot! Temperature: " + temperature + "°C");
        }
    }
}
