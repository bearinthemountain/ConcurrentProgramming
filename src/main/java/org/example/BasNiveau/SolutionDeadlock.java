package org.example.BasNiveau;

public class SolutionDeadlock {
    static final Object ressourceA = new Object();
    static final Object ressourceB = new Object();

    // Pour le Thread 1
    Runnable tache1 = () -> {
        synchronized (ressourceA) { // On prend A en premier
            System.out.println("Tache 1 a verrouillé A");
            synchronized (ressourceB) { // Puis B
                System.out.println("Tache 1 a verrouillé B, elle travaille !");
            }
        }
    };

    // Pour le Thread 2 (LA CORRECTION EST ICI)
    // Au lieu de faire B puis A, il DOIT faire A puis B, comme le Thread 1.
    Runnable tache2 = () -> {
        synchronized (ressourceA) { // On prend A en premier (MÊME ORDRE !)
            System.out.println("Tache 2 a verrouillé A");
            synchronized (ressourceB) { // Puis B
                System.out.println("Tache 2 a verrouillé B, elle travaille !");
            }
        }
    };
}