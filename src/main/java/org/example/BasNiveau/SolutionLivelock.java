package org.example.BasNiveau;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
La situation à l'examen : On te montre deux threads qui utilisent tryLock(). Quand ils n'arrivent pas à avoir tous les outils, ils lâchent tout, attendent (par exemple) 10 millisecondes, et réessaient. Le problème, c'est qu'ils le font exactement en même temps en boucle, donc ils échouent en boucle.
La solution : Briser la symétrie. Tu dois remplacer le temps d'attente fixe par un temps d'attente aléatoire. Ainsi, un thread réessaiera un peu avant l'autre, ce qui cassera le cycle infernal.

Le code de la solution :


 */

public class SolutionLivelock {
    static Lock verrou = new ReentrantLock();
    static Random aleatoire = new Random();

    Runnable tache = () -> {
        while (true) {
            if (verrou.tryLock()) {
                try {
                    System.out.println("Verrou acquis, travail en cours...");
                    break; // Succès, on sort de la boucle
                } finally {
                    verrou.unlock();
                }
            } else {
                // LA CORRECTION EST ICI : Le Random Backoff
                // Au lieu de faire Thread.sleep(50) (qui crée le livelock),
                // on attend un temps imprévisible.
                try {
                    int attente = aleatoire.nextInt(100); // Entre 0 et 99 ms
                    Thread.sleep(attente);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    };
}