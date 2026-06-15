package org.example.BasNiveau;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SolutionStarvation {

    // LA CORRECTION EST ICI :
    // Le "true" crée un verrou équitable (Fair Lock).
    // Le thread qui attend depuis le plus longtemps passera obligatoirement en premier.
    static final Lock verrouEquitable = new ReentrantLock(true);

    Runnable tache = () -> {
        // On remplace le mot-clé "synchronized(verrouEquitable)" par ceci :
        verrouEquitable.lock(); // On fait la queue poliment
        try {
            System.out.println(Thread.currentThread().getName() + " travaille enfin !");
            // Simulation du travail...
        } finally {
            // Indispensable : toujours libérer le verrou dans un bloc finally
            // pour être sûr de le rendre même en cas d'erreur.
            verrouEquitable.unlock();
        }
    };
}