package org.example.Exercices.CountDownLatchFamilyTrip;

/*
sout : Génère System.out.println();

psvm : C'est une alternative directe pour public static void main(String[] args).

fori : Génère une boucle for classique avec un index.

iter : Génère une boucle for-each pour parcourir une collection ou un tableau.

main

alt + enter -> sur une erreur pour la solution
 */

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        String[] family = {"Jean", "Anna", "Joseph", "Martha", "Eleonore", "Paul", "Catarina"};
        int nbTaches = family.length;

        CountDownLatch loquet = new CountDownLatch(nbTaches);
        System.out.println("---countdown latch----");

        // création d'un thread par personne
        // 2. Création et démarrage d'un thread par membre
        for (String membre : family) {
            new Thread(() -> {
                try {
                    // Simulation du temps de chargement
                    Thread.sleep((long) (Math.random() * 1000));
                    System.out.println(membre + " a chargé sa valise.");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    // 3. Décrémentation du loquet
                    loquet.countDown();
                }
            }).start();
        }
        loquet.await();

        System.out.println("tout le monde a chargé");
        System.out.println("Le voyage en famille peut commencer");
    }
}
