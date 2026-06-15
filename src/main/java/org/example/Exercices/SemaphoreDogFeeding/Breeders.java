package org.example.Exercices.SemaphoreDogFeeding;
// 1. Constructeur : Initialise le sémaphore
// new Semaphore(int permits);
// - permits : Nombre initial de jetons (0 pour bloquer, 1 pour un verrou/mutex).
// new Semaphore(int permits, boolean fair);
// - fair : Si true, le sémaphore garantit l'ordre d'arrivée des threads (FIFO).

// 2. Acquisition : Demande un jeton
// sem.acquire();
// - Bloque le thread tant qu'un jeton n'est pas disponible. Interrompable.

// 3. Libération : Rend un jeton
// sem.release();
// - Augmente le nombre de jetons disponibles, réveillant un thread en attente.

// 4. Acquisition sécurisée (Non bloquante)
// sem.tryAcquire();
// - Retourne 'true' si un jeton a été pris immédiatement, 'false' sinon (ne bloque pas).
// sem.tryAcquire(long timeout, TimeUnit unit);
// - Tente de prendre un jeton pendant un temps donné avant d'abandonner.

// 5. Inspection
// sem.availablePermits();
// - Retourne le nombre actuel de jetons disponibles (utile pour le debug).

import java.util.concurrent.Semaphore;

public class Breeders implements Runnable {
    private String name;
    private Bowl[] bowls;
    private Semaphore[] semaphores;

    public Breeders(String name, Bowl[] bowls, Semaphore[] semaphores) {
        this.name = name;
        this.bowls = bowls;
        this.semaphores = semaphores;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000); // Temps de préparation de la nourriture

                for (int i = 0; i < bowls.length; i++) {
                    // On vérifie si la gamelle est vide
                    if (bowls[i].isEmpty()) {
                        bowls[i].setEmpty(false); // On la remplit
                        System.out.println(name + " remplit la gamelle " + i);

                        // On libère le sémaphore pour autoriser un chien à manger
                        semaphores[i].release();
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Bonne pratique pour gérer les interruptions
                break;
            }
        }
    }

}