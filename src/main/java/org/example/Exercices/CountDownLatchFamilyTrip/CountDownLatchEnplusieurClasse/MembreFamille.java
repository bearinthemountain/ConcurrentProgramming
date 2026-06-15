package org.example.Exercices.CountDownLatchFamilyTrip.CountDownLatchEnplusieurClasse;

import java.util.concurrent.CountDownLatch;

/*
1. La classe MembreFamille (Le "Travailleur")
Cette classe implémente Runnable.
Elle ne sait pas combien de personnes il y a,
elle sait juste qu'elle doit faire une action et signaler au
loquet qu'elle a fini.

 */

public class MembreFamille implements Runnable {
    private final String nom;
    private final CountDownLatch latch;

    public MembreFamille(String nom, CountDownLatch latch) {
        this.nom = nom;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            // Simulation du travail
            Thread.sleep((long) (Math.random() * 1000));
            System.out.println(nom + " a chargé sa valise.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            // Indique que ce membre a terminé
            latch.countDown();
        }
    }
}