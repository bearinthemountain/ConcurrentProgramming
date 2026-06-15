package org.example.HautNiveau;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/*
1. Comprendre la BlockingQueue
Une BlockingQueue est une file d'attente "intelligente" spécialement conçue pour les problèmes de type Producteur-Consommateur. Elle gère automatiquement les cas critiques où la file est pleine ou vide, vous évitant d'écrire des blocs synchronized ou d'utiliser wait() et notify().

Le principe de base : C'est une structure "First-In, First-Out" (FIFO). Le premier élément ajouté est le premier à être retiré.

Deux types principaux :

Unbounded Queue : Elle peut grandir indéfiniment (la limite théorique est Integer.MAX_VALUE). Elle ne bloque donc (presque) jamais les producteurs. Exemple d'implémentation : LinkedBlockingQueue (sans argument de capacité).

Bounded Queue : Elle a une capacité maximale définie lors de sa création. C'est le type le plus courant car il empêche une surconsommation de mémoire si les producteurs sont trop rapides. Exemple d'implémentation : ArrayBlockingQueue ou LinkedBlockingQueue(capacité).

2. Les méthodes clés (à mémoriser pour l'examen)
Pour que la BlockingQueue agisse comme une file d'attente bloquante, vous devez utiliser des méthodes spécifiques. Si vous utilisez les méthodes habituelles des collections (comme add() ou remove()), elle ne bloquera pas !

Ajouter un élément (Producteur) : put(e)

Que fait-elle ? Elle insère l'élément à la fin de la file.

Le comportement "bloquant" : Si la file est pleine (dans le cas d'une Bounded Queue), le thread producteur est mis en pause (bloqué) jusqu'à ce qu'un consommateur libère de la place.

Retirer un élément (Consommateur) : take()

Que fait-elle ? Elle retire et renvoie l'élément situé en tête de file.

Le comportement "bloquant" : Si la file est vide, le thread consommateur est mis en pause (bloqué) jusqu'à ce qu'un producteur ajoute un nouvel élément.

 */

public class LabBlockingQueue {

    // --- 1. LE PRODUCTEUR ---
    // Il implémente Runnable pour être exécuté par un Thread
    static class Producer implements Runnable {
        private final BlockingQueue<String> queue;
        private final int id;

        public Producer(BlockingQueue<String> q, int id) {
            this.queue = q;
            this.id = id;
        }

        @Override
        public void run() {
            try {
                for (int i = 1; i <= 3; i++) { // Chaque producteur crée 3 éléments
                    String element = "Item-" + i + " from Producer-" + id;
                    System.out.println("Producer-" + id + " tente d'ajouter: " + element);

                    // METHODE CLE : put() bloque si la file est pleine
                    queue.put(element);

                    System.out.println("Producer-" + id + " a ajouté avec succès.");
                    Thread.sleep(500); // Simule le temps de production
                }
            } catch (InterruptedException e) {
                // InterruptedException est levée si le thread est interrompu pendant qu'il est bloqué (dans put() ou sleep())
                Thread.currentThread().interrupt();
                System.out.println("Producer-" + id + " interrompu.");
            }
        }
    }

    // --- 2. LE CONSOMMATEUR ---
    static class Consumer implements Runnable {
        private final BlockingQueue<String> queue;
        private final int id;

        public Consumer(BlockingQueue<String> q, int id) {
            this.queue = q;
            this.id = id;
        }

        @Override
        public void run() {
            try {
                // Le consommateur tourne souvent en boucle infinie (ou jusqu'à un signal d'arrêt)
                while (true) {
                    System.out.println("Consumer-" + id + " attend un élément...");

                    // METHODE CLE : take() bloque si la file est vide
                    String element = queue.take();

                    System.out.println("Consumer-" + id + " a consommé: " + element);
                    Thread.sleep(1000); // Simule le temps de traitement de l'élément
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Consumer-" + id + " interrompu.");
            }
        }
    }

    // --- 3. LE PROGRAMME PRINCIPAL ---
    public static void main(String[] args) {

        // Création d'une Bounded Queue (Capacité maximale de 2 éléments)
        // C'est crucial : si la capacité est petite, on voit bien l'effet bloquant du put()
        BlockingQueue<String> sharedQueue = new LinkedBlockingQueue<>(2);

        System.out.println("Lancement du système (Capacité de la file : 2)...");

        // Création de 2 Producteurs
        Thread p1 = new Thread(new Producer(sharedQueue, 1));
        Thread p2 = new Thread(new Producer(sharedQueue, 2));

        // Création de 1 Consommateur (il sera plus lent que les 2 producteurs combinés)
        Thread c1 = new Thread(new Consumer(sharedQueue, 1));

        // Lancement des threads
        c1.start();
        p1.start();
        p2.start();

        // (Optionnel) Arrêter le programme après un certain temps pour l'exemple
        try {
            Thread.sleep(5000);
            System.out.println("Arrêt du système.");
            p1.interrupt();
            p2.interrupt();
            c1.interrupt(); // Interrompt le consommateur qui est probablement bloqué dans take()
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}