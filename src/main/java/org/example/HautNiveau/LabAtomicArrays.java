package org.example.HautNiveau;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class LabAtomicArrays {

    public static void main(String[] args) {

        // 1. CRÉATION DES TABLEAUX
        // Tableau standard (Dangereux en multithread)
        int[] tableauStandard = {0, 1, 2, 3, 4};

        // Tableau Atomique (Sécurisé : chaque case se comporte comme un AtomicInteger)
        AtomicIntegerArray tableauAtomique = new AtomicIntegerArray(new int[]{0, 1, 2, 3, 4});

        System.out.println("Valeurs de départ du tableau atomique : " + tableauAtomique);
        System.out.println("Valeurs de départ du tableau standard : " + Arrays.toString(tableauStandard));

        // 2. LA TÂCHE (Incrémenter chaque case 100 000 fois)
        int nombreIncrements = 100_000;

        Runnable tacheIncrementation = () -> {
            for (int i = 0; i < nombreIncrements; i++) {
                for (int j = 0; j < 5; j++) {
                    // Modification NON-SÉCURISÉE (Risque de collision)
                    tableauStandard[j]++;

                    // Modification SÉCURISÉE (Atomique, garantie sans collision)
                    tableauAtomique.incrementAndGet(j);
                }
            }
        };

        // 3. CRÉATION DE 3 THREADS
        System.out.println("Création de 3 threads pour incrémenter les valeurs avec ces deux tableaux...");
        Thread t1 = new Thread(tacheIncrementation);
        Thread t2 = new Thread(tacheIncrementation);
        Thread t3 = new Thread(tacheIncrementation);

        // Lancement simultané
        t1.start();
        t2.start();
        t3.start();

        // On oblige le Main (programme principal) à attendre que les 3 aient fini
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 4. AFFICHAGE DES RÉSULTATS
        // Chaque case devait être incrémentée 100 000 fois par 3 threads.
        // Résultat final attendu : valeurInitiale + 300 000.
        System.out.println("\nValeurs finales du tableau atomique : " + tableauAtomique);
        System.out.println("Valeurs finales du tableau standard : " + Arrays.toString(tableauStandard));
    }
}
/*
1. Quand utiliser une variable Atomic ?
Tu dois utiliser une variable du package java.util.concurrent.atomic (comme AtomicInteger, AtomicBoolean ou AtomicIntegerArray) dans ce cas précis :

Tu as une SEULE variable partagée (ex: un compteur de visites, un score, un index de tableau) qui est lue et modifiée en permanence par plusieurs threads en même temps.

Tu veux des performances maximales sans utiliser les verrous lourds comme synchronized ou ReentrantLock.

2. L'explication : La grande illusion du c++
Sur ta première diapo, le professeur montre le fameux c++. À l'examen, c'est le piège numéro 1.
En Java, l'opération c++ n'est pas une seule action. C'est une opération en 3 étapes :

Lire la valeur actuelle de c.

Ajouter 1 à cette valeur.

Écrire la nouvelle valeur dans c.

Le problème sans Atomic : Si 2 threads font c++ exactement en même temps sur c=0... Ils vont tous les deux lire 0, tous les deux calculer 0+1, et tous les deux écrire 1. Résultat : c vaut 1 au lieu de 2. Tu as "perdu" une incrémentation.

La solution Atomic : AtomicInteger utilise des instructions directes au niveau du processeur (matériel) pour garantir que ces 3 étapes soient fusionnées en une seule opération indivisible (Atomique). Il est impossible que deux threads se marchent sur les pieds.

3. Le Code Modèle : Le Lab S2 (Standard vs Atomic Arrays)
Voici la résolution complète de l'exercice de ta deuxième diapositive. Ce code prouve visuellement que les tableaux standards perdent des données, contrairement aux tableaux atomiques.


 */