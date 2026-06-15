package org.example.BasNiveau;

// 1. Première classe : La tâche à exécuter
class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Thread started:::" + Thread.currentThread().getName());
        try {
            Thread.sleep(4000); // Fait une pause de 4 secondes
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread ended:::" + Thread.currentThread().getName());
    }
} // <-- IMPORTANT : L'accolade de MyRunnable se ferme ICI.

// 2. Deuxième classe : Le programme principal
public class ThreadJoinExample {
    public static void main(String[] args) {
        Thread t1 = new Thread(new MyRunnable(), "t1");
        Thread t2 = new Thread(new MyRunnable(), "t2");
        Thread t3 = new Thread(new MyRunnable(), "t3");

        // Démarrage de t1
        t1.start();

        try {
            // Le thread Main attend au maximum 2000ms (2s) que t1 finisse.
            // Comme t1 dure 4s, le main va reprendre son exécution avant la fin de t1.
            t1.join(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Démarrage de t2 (t1 est toujours en cours d'exécution à ce moment-là)
        t2.start();

        try {
            // Le thread Main attend infiniment que t1 soit complètement terminé
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // t3 ne démarre QUE lorsque t1 est totalement terminé
        t3.start();

        try {
            // Le main attend que tout le monde ait terminé avant de faire sa dernière phrase
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All threads are dead, exiting main thread");
    }
}