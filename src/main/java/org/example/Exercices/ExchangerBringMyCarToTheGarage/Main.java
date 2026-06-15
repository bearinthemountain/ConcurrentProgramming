package org.example.Exercices.ExchangerBringMyCarToTheGarage;

import java.util.concurrent.Exchanger;

public class Main {
    public static void main(String[] args) {

        Exchanger<Car> carExchanger = new Exchanger<>();


        new Thread(()-> {
            try {
                Car maVoiture = new Car("Alpha");
                System.out.println("J'amène ma voiture : " + maVoiture);

                // Le client donne sa voiture, récupère celle du garagiste
                Car voitureRecue = carExchanger.exchange(maVoiture);

                System.out.println("Je repars avec -> " + voitureRecue);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }).start();

        new Thread(() -> {
            try{
                Car voitureDePret = new Car("Bolide de prêt");
                System.out.println("Garagiste : J'attends la voiture du client...");

                // Le garagiste donne la voiture de prêt, récupère celle du client
                Car voitureRecue = carExchanger.exchange(voitureDePret);

                System.out.println("Garagiste : Je répare la " + voitureRecue);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}
