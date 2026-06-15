package org.example.Exercices.SemaphoreDogFeeding;

import java.util.concurrent.Semaphore;

public class TestingSemaphore_DogBreeder {
    public static void main(String[] args) {
        Bowl[] bowls = new Bowl[10];
        Semaphore[] semaphores = new Semaphore[10];

        // 1. Initialisation
        for (int i = 0; i < 10; i++) {
            bowls[i] = new Bowl();
            bowls[i].setEmpty(true);
            semaphores[i] = new Semaphore(0); // Les chiens attendent le release()
        }

        // 2. Lancement des Breeders (les humains)
        new Thread(new Breeders("Marco", bowls, semaphores)).start();
        new Thread(new Breeders("Luisa", bowls, semaphores)).start();

        // 3. Lancement des 9 chiens
        for (int i = 1; i <= 9; i++) {
            new Thread(new Dogs("Dog" + i, bowls, semaphores)).start();
        }
    }
}