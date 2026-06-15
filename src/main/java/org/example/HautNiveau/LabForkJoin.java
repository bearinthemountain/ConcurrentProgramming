package org.example.HautNiveau;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.Random;

public class LabForkJoin {

    // 1. LA CLASSE DE TÂCHE (Le cœur du système)
    // On utilise RecursiveTask<Long> car on veut RETOURNER un résultat (la somme).
    // Si on voulait juste modifier le tableau sans rien renvoyer, on utiliserait RecursiveAction.
    static class SommeTableauTask extends RecursiveTask<Long> {

        // Les informations dont la tâche a besoin pour travailler
        private final int[] tableau;
        private final int debut;
        private final int fin;

        // LE SEUIL (Threshold) : À partir de quand on arrête de diviser ?
        // C'est la ligne "if (sizeOfProblem < threshold)" de ta diapo.
        private static final int SEUIL = 10_000;

        public SommeTableauTask(int[] tableau, int debut, int fin) {
            this.tableau = tableau;
            this.debut = debut;
            this.fin = fin;
        }

        // C'est ici que la magie opère !
        @Override
        protected Long compute() {
            int tailleDuProbleme = fin - debut;

            // CAS DE BASE : Le problème est assez petit, on le résout directement (Séquentiellement)
            if (tailleDuProbleme <= SEUIL) {
                long sommeLocale = 0;
                for (int i = debut; i < fin; i++) {
                    sommeLocale += tableau[i];
                }
                return sommeLocale;
            }

            // CAS RÉCURSIF : Le problème est trop gros, on le coupe en deux (Fork)
            else {
                int milieu = debut + (tailleDuProbleme / 2);

                // On crée les deux sous-tâches
                SommeTableauTask tacheGauche = new SommeTableauTask(tableau, debut, milieu);
                SommeTableauTask tacheDroite = new SommeTableauTask(tableau, milieu, fin);

                // ÉTAPE 1 : FORK (On lance la tâche gauche en arrière-plan sur un autre thread)
                tacheGauche.fork();

                // ÉTAPE 2 : COMPUTE (On calcule la tâche droite nous-même sur NOTRE thread actuel pour ne pas chômer)
                long resultatDroit = tacheDroite.compute();

                // ÉTAPE 3 : JOIN (On attend que l'autre thread ait fini la tâche gauche et on récupère son résultat)
                long resultatGauche = tacheGauche.join();

                // ÉTAPE 4 : MERGE (On fusionne les résultats finaux)
                return resultatGauche + resultatDroit;
            }
        }
    }

    // 2. LE PROGRAMME PRINCIPAL (Test)
    public static void main(String[] args) {
        // A. Création d'un tableau géant de 50 millions de cases
        System.out.println("Création du tableau géant...");
        int TAILLE = 50_000_000;
        int[] grandTableau = new int[TAILLE];
        Random random = new Random();
        for (int i = 0; i < TAILLE; i++) {
            grandTableau[i] = random.nextInt(10); // Des nombres entre 0 et 9
        }

        // B. Configuration du Pool (Setting up the Pool)
        ForkJoinPool pool = new ForkJoinPool();

        // C. Création de la tâche racine (qui englobe tout le tableau de 0 à la fin)
        SommeTableauTask tachePrincipale = new SommeTableauTask(grandTableau, 0, grandTableau.length);

        System.out.println("Lancement du calcul parallèle...");
        long tempsDebut = System.currentTimeMillis();

        // D. Lancement via la méthode invoke()
        long resultatTotal = pool.invoke(tachePrincipale);

        long tempsFin = System.currentTimeMillis();

        System.out.println("Résultat final : " + resultatTotal);
        System.out.println("Temps de calcul : " + (tempsFin - tempsDebut) + " ms");
    }
}