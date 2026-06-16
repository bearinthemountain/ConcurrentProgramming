package org.example.Exercices.SynchronizedRaceFormerExam;
/*
Le "Super-Pouvoir" de CyclicBarrier : La Barrier Action
Une CyclicBarrier possède un constructeur unique qui accepte une action (un Runnable) à exécuter automatiquement dès que le dernier coureur passe la barrière, et avant que quiconque ne soit libéré pour l'étape suivante.

Pour ton problème d'affichage (le fameux "ordre de golmon"), la CyclicBarrier règle le problème élégamment sans avoir besoin de faire une double barrière avec le thread Main :

Java
// Dans le Main, on définit la barrière avec l'action de fin d'étape
CyclicBarrier barrier = new CyclicBarrier(runners.length, () -> {
    // Ce code est exécuté par le TOUT DERNIER thread qui arrive,
    // EXACTEMENT au moment où l'étape se termine !
    System.out.println(">>> Everyone is arrived <<< \n");
});
Dans le Runner, il suffit d'un simple barrier.await(); en fin de boucle. Pas de jaloux, pas de threads qui se marchent dessus, l'affichage de fin est garanti d'être au bon endroit.


 */

import java.util.concurrent.Phaser;

public class Main {
    public static void main(String[] args) {
        String[] runners = {"Yamaha", "Honda", "Harley", "KTM", "Bob"};
        String[] races = {"ThyonDixence", "Les chateaux", "Anzere", "Piste de l'ours"};

        // Initialisation à 1 pour inclure le thread Main dans la synchronisation
        Phaser phaser = new Phaser(1);

        // Instanciation et lancement des coureurs
        for (String name : runners) {
            Runner runner = new Runner(name, races, phaser);
            new Thread(runner).start();
        }

        // Orchestration stricte de la course
        for (String race : races) {
            // TOP DÉPART : Libère les coureurs pour qu'ils affichent leur départ en même temps
            phaser.arriveAndAwaitAdvance();

            // ATTENTE DE FIN : Bloque le Main tant que les coureurs n'ont pas tous fini l'étape
            phaser.arriveAndAwaitAdvance();

            // Garanti d'être affiché à la toute fin de l'étape en cours
            System.out.println(">>> Everyone is arrived " + race + " <<<\n");
        }

        phaser.arriveAndDeregister(); // Désenregistrement du Main
        System.out.println("Race successfully finished! All runners completed the event.");
    }
}