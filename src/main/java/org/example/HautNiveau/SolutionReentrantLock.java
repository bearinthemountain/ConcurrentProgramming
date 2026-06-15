package org.example.HautNiveau;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SolutionReentrantLock {

    // On crée la classe Ami en remplaçant 'synchronized' par 'ReentrantLock'
    static class Ami {
        private final String nom;
        // Chaque ami possède son propre verrou (Lock)
        private final Lock verrou = new ReentrantLock();

        public Ami(String nom) {
            this.nom = nom;
        }

        public String getNom() {
            return this.nom;
        }

        // ==========================================================
        // LA MÉTHODE MAGIQUE : Tenter de prendre les DEUX verrous
        // ==========================================================
        public boolean tenterDeSaluer(Ami autreAmi) {
            boolean monVerrouAcquis = false;
            boolean sonVerrouAcquis = false;

            try {
                // ÉTAPE 1 : J'essaie de me verrouiller moi-même (sans bloquer)
                monVerrouAcquis = this.verrou.tryLock();

                // ÉTAPE 2 : J'essaie de verrouiller l'autre (sans bloquer)
                sonVerrouAcquis = autreAmi.verrou.tryLock();

            } finally {
                // ÉTAPE 3 : La condition anti-deadlock !
                // Si je n'ai PAS réussi à avoir les DEUX verrous en même temps...
                if (!(monVerrouAcquis && sonVerrouAcquis)) {
                    // ... je relâche immédiatement celui que j'avais réussi à prendre.
                    // C'est ça qui empêche l'impasse !
                    if (monVerrouAcquis) this.verrou.unlock();
                    if (sonVerrouAcquis) autreAmi.verrou.unlock();
                }
            }

            // Renvoie 'true' UNIQUEMENT si j'ai attrapé les deux clés.
            return monVerrouAcquis && sonVerrouAcquis;
        }

        // ==========================================================
        // LES ACTIONS
        // ==========================================================
        public void saluer(Ami autreAmi) {
            // Au lieu de bloquer avec 'synchronized', on demande poliment
            if (tenterDeSaluer(autreAmi)) {
                try {
                    System.out.println(this.nom + " : Bonjour très cher " + autreAmi.getNom() + " !");
                    autreAmi.rendreLeSalut(this); // L'autre répond
                } finally {
                    // On a fini, on relâche les deux verrous
                    this.verrou.unlock();
                    autreAmi.verrou.unlock();
                }
            } else {
                // Si on entre ici, c'est qu'un Deadlock a été ÉVITÉ !
                System.out.println(this.nom + " : Oups, " + autreAmi.getNom() + " allait me saluer aussi. Je recule !");
            }
        }

        public void rendreLeSalut(Ami autreAmi) {
            System.out.println(this.nom + " : Bonjour à vous aussi, " + autreAmi.getNom() + " !");
        }
    }

    // ==========================================================
    // LE PROGRAMME PRINCIPAL (Test)
    // ==========================================================
    public static void main(String[] args) {
        final Ami alphonse = new Ami("Alphonse");
        final Ami gaston = new Ami("Gaston");

        // Alphonse essaie de saluer Gaston en boucle
        Thread threadAlphonse = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                alphonse.saluer(gaston);
                try { Thread.sleep(new Random().nextInt(50)); } catch (InterruptedException e) {}
            }
        });

        // Gaston essaie de saluer Alphonse en boucle
        Thread threadGaston = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                gaston.saluer(alphonse);
                try { Thread.sleep(new Random().nextInt(50)); } catch (InterruptedException e) {}
            }
        });

        // Le combat de politesse commence !
        threadAlphonse.start();
        threadGaston.start();
    }
}