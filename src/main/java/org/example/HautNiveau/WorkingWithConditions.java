package org.example.HautNiveau;

import java.util.Stack;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class WorkingWithConditions {

    // La ressource partagée (Une étagère avec une place limitée à 5)
    static class Etagere {
        private final Stack<String> pile = new Stack<>();
        private final int CAPACITE_MAX = 5;

        // 1. Le verrou principal
        private final ReentrantLock lock = new ReentrantLock();

        // 2. Les DEUX salles d'attente (Conditions) liées à ce verrou
        private final Condition attentePlaceLibre = lock.newCondition();
        private final Condition attenteNouvelObjet = lock.newCondition();

        // --- MÉTHODE PRODUCTEUR ---
        public void deposer(String objet) {
            lock.lock(); // On verrouille
            try {
                // LA GARDE : Si l'étagère est pleine, le producteur va dans la salle "attentePlaceLibre"
                while (pile.size() == CAPACITE_MAX) {
                    attentePlaceLibre.await(); // <- Piège : c'est await(), pas wait() !
                }

                // On dépose l'objet
                pile.push(objet);
                System.out.println("Déposé : " + objet);

                // LA NOTIFICATION CIBLÉE : On réveille UNIQUEMENT les consommateurs !
                attenteNouvelObjet.signalAll(); // <- Piège : c'est signalAll(), pas notifyAll() !

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock(); // On déverrouille toujours dans le finally
            }
        }

        // --- MÉTHODE CONSOMMATEUR ---
        public String retirer() {
            lock.lock(); // On verrouille
            try {
                // LA GARDE : Si l'étagère est vide, le consommateur va dans la salle "attenteNouvelObjet"
                while (pile.size() == 0) {
                    attenteNouvelObjet.await();
                }

                // On retire l'objet
                String objet = pile.pop();
                System.out.println("Retiré : " + objet);

                // LA NOTIFICATION CIBLÉE : On réveille UNIQUEMENT les producteurs !
                attentePlaceLibre.signalAll();

                return objet;

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            } finally {
                lock.unlock();
            }
        }
    }

    // Test rapide pour vérifier que ça fonctionne
    public static void main(String[] args) {
        Etagere etagere = new Etagere();

        // Un thread qui dépose 10 objets très vite
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) etagere.deposer("Objet " + i);
        }).start();

        // Un thread qui retire 10 objets lentement
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                etagere.retirer();
                try { Thread.sleep(500); } catch (InterruptedException e) {}
            }
        }).start();
    }
}