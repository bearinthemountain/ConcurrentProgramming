package org.example.Exercices.SynchronizedRaceFormerExam;

import java.util.concurrent.Phaser;
import java.util.Random;

public class Runner implements Runnable {
    private String name;
    private String[] races;
    private Phaser phaser;

    public Runner(String name, String[] races, Phaser phaser) {
        this.name = name;
        this.races = races;
        this.phaser = phaser;
        this.phaser.register(); // Le runner s'enregistre auprès du phaser
    }

    @Override
    public void run() {
        for (String race : races) {
            // 1. BARRIÈRE DE DÉPART : On attend le top départ du Main
            phaser.arriveAndAwaitAdvance();

            System.out.println(name + " starts the stage: " + race);

            try {
                // Délai obligatoire de 1 à 5 secondes (1000ms + entre 0 et 4000ms)
                Thread.sleep(1000 + new Random().nextInt(4000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            System.out.println(name + " finished " + race);

            // 2. BARRIÈRE DE FIN : On attend que tout le monde ait fini l'étape
            phaser.arriveAndAwaitAdvance();
        }
        phaser.arriveAndDeregister(); // Quitte définitivement la course
    }
}