package org.example.BasNiveau;

// 1. La ressource partagée
class CompteurPartage {
    int valeur = 0;

    // Cette méthode est le point faible. Elle n'est pas "synchronized".
    // L'opération valeur++ se fait en 3 étapes : Lire, Ajouter 1, Sauvegarder.
    public synchronized void incrementer() {
        valeur++;
    }
}

/*
correction
public synchronized void incrementer() {
        valeur++;
    }
}

 */

// 2. Le programme principal
public class ThreadInterferenceExam {

    public static void main(String[] args) {
        // On crée UN SEUL compteur qui sera partagé par les threads
        CompteurPartage compteur = new CompteurPartage();

        // On crée la tâche : incrémenter le compteur 10 000 fois
        Runnable tache = () -> {
            for (int i = 0; i < 10000; i++) {
                compteur.incrementer();
            }
        };

        // On embauche deux "ouvriers" (Threads) et on leur donne LA MÊME tâche
        Thread threadA = new Thread(tache, "Thread-A");
        Thread threadB = new Thread(tache, "Thread-B");

        // Top départ : ils commencent à modifier le compteur en même temps
        threadA.start();
        threadB.start();

        // Le thread Main attend sagement qu'ils aient terminé leurs 10 000 tours
        try {
            threadA.join();
            threadB.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // --- RÉSULTAT POUR L'EXAMEN ---
        // Si tout se passait bien : 10 000 + 10 000 = 20 000.
        // Mais à cause de l'interférence, des milliers d'opérations vont s'écraser.
        System.out.println("--- DÉMONSTRATION D'INTERFÉRENCE ---");
        System.out.println("Résultat attendu  : 20000");
        System.out.println("Résultat obtenu   : " + compteur.valeur);
        // Tu verras que le résultat obtenu est un nombre aléatoire (ex: 13452, 11045...)
        // et il change à chaque fois que tu lances le programme !
    }
}