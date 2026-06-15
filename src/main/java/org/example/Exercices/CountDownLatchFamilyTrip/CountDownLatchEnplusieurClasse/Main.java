package org.example.Exercices.CountDownLatchFamilyTrip.CountDownLatchEnplusieurClasse;


import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String[] family = {"Jean", "Anna", "Joseph", "Martha", "Eleonore", "Paul", "Catarina"};

        CountDownLatch loquet = new CountDownLatch(family.length);

        System.out.println("--- Début du voyage ---");

        // Lancement des threads
        for (String nom : family) {
            Thread thread = new Thread(new MembreFamille(nom, loquet));
            thread.start();
        }

        // Le main attend ici la fin de tous les threads
        loquet.await();

        System.out.println("Tout le monde est prêt !");
        System.out.println("Le voyage en famille peut commencer.");
    }
}