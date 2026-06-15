package org.example.Exercices.CarsSingleton;

public class TemperatureSensor implements Sensor{

    private String chaleur = "fait chaud";

    @Override
    public String getData() {
        return this.chaleur;
    }
}
