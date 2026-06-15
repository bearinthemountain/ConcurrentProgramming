package org.example.Exercices.CarsSingleton;

public class SpeedSensor implements Sensor{

    private String data = "tu vas vite";


    @Override
    public String getData() {
        return this.data;
    }
}
