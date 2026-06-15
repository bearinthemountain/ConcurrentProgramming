package org.example.BasNiveau;
import java.util.Stack; // Ne pas oublier d'importer la classe !

public class JoinsStack {
    public static void main(String[] args) {
        // 1. Création de nos deux piles
        Stack<String> sourceStack = new Stack<>();
        Stack<String> targetStack = new Stack<>();

        // 2. On remplit la pile source
        sourceStack.push("Pièce A");
        sourceStack.push("Pièce B");
        sourceStack.push("Pièce C");

        System.out.println("--- AVANT LE TRANSFERT ---");
        System.out.println("Source contient : " + sourceStack);
        System.out.println("Cible contient  : " + targetStack + "\n");

        // 3. On crée la tâche de transfert
        Runnable transferTask = () -> {
            System.out.println(Thread.currentThread().getName() + " commence le travail...");

            // Tant que la pile source n'est pas vide
            while (!sourceStack.isEmpty()) {
                // On retire (pop) de la source et on ajoute (push) à la cible
                String piece = sourceStack.pop();
                targetStack.push(piece);

                System.out.println(" -> Déplacement de " + piece);

                // Petite pause pour simuler le temps de transport
                try { Thread.sleep(500); } catch (InterruptedException e) { return; }
            }
        };

        // 4. On engage un ouvrier (Thread) et on le lance
        Thread workerThread = new Thread(transferTask, "Ouvrier-1");
        workerThread.start();

       /*// 5. L'ÉTAPE CRUCIALE : Le Join
        try {
            // Le chef de chantier (Main Thread) attend que l'ouvrier ait fini
            workerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        // 6. Vérification finale (Ne s'affiche que lorsque workerThread a terminé)
        System.out.println("\n--- APRÈS LE TRANSFERT ---");
        System.out.println("Source contient : " + sourceStack);
        System.out.println("Cible contient  : " + targetStack);
    }
}
