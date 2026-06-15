package org.example.BasNiveau;

public class Deadlock {

    // 1. On crée nos deux ressources (de simples objets qui serviront de verrous)
    static final Object MANETTE = new Object();
    static final Object TELEVISION = new Object();

    public static void main(String[] args) {

        // 2. La tâche d'Alphonse
        Runnable tacheAlphonse = () -> {
            // Alphonse prend la manette en premier
            synchronized (MANETTE) {
                System.out.println("Alphonse : J'ai la manette ! J'attends la télé...");

                try { Thread.sleep(100); } catch (InterruptedException e) {}

                // Tout en gardant la manette, il essaie de prendre la télé
                synchronized (TELEVISION) {
                    System.out.println("Alphonse : J'ai les deux, je joue !");
                }
            } // Il lâche tout ici (mais il n'y arrivera jamais)
        };

        // 3. La tâche de Dylan (L'ordre est inversé !)
        Runnable tacheDylan = () -> {
            // Dylan prend la télé en premier
            synchronized (TELEVISION) {
                System.out.println("Dylan : J'ai la télé ! J'attends la manette...");

                try { Thread.sleep(100); } catch (InterruptedException e) {}

                // Tout en gardant la télé, il essaie de prendre la manette
                synchronized (MANETTE) {
                    System.out.println("Dylan : J'ai les deux, je joue !");
                }
            }
        };

        // 4. On crée et on lance nos deux têtus
        Thread alphonseThread = new Thread(tacheAlphonse);
        Thread dylanThread = new Thread(tacheDylan);

        System.out.println("Main : C'est parti, le combat commence...");
        alphonseThread.start();
        dylanThread.start();

        // Le programme ne s'arrêtera jamais tout seul ! Tu devras cliquer sur le carré rouge 🟥
    }
}