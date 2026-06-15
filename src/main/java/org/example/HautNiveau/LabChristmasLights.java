package org.example.HautNiveau;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LabChristmasLights {

    public static void main(String[] args) {
        System.out.println("🎄 Allumage des décorations de Noël...");

        // 1. CRÉATION DU SCHEDULER
        // 3 guirlandes à gérer = un pool de 3 threads
        ScheduledExecutorService programmateur = Executors.newScheduledThreadPool(3);

        // 2. CRÉATION DES TÂCHES (Les guirlandes)
        // On simule l'état (allumé/éteint) directement dans le Runnable si on le souhaite,
        // ou on fait juste un affichage pour simuler un "clignotement" (Flicker).
        Runnable guirlandeRouge = () -> System.out.println("🔴 Clignotement ROUGE !");
        Runnable guirlandeVerte = () -> System.out.println("   🟢 Clignotement VERT !");
        Runnable guirlandeEtoile = () -> System.out.println("      ⭐ FLASH DE L'ÉTOILE FILANTE !");

        // 3. PLANIFICATION DES EFFETS VISUELS
        // La guirlande rouge clignote rapidement toutes les 1 secondes
        programmateur.scheduleAtFixedRate(guirlandeRouge, 0, 1, TimeUnit.SECONDS);

        // La guirlande verte clignote toutes les 2 secondes (plus lente)
        programmateur.scheduleAtFixedRate(guirlandeVerte, 1, 2, TimeUnit.SECONDS);

        // L'étoile filante ne flashe que toutes les 4 secondes, pour créer un effet de surprise
        programmateur.scheduleAtFixedRate(guirlandeEtoile, 2, 4, TimeUnit.SECONDS);

        // 4. TEMPS D'EXÉCUTION
        // On laisse les illuminations tourner pendant 10 secondes
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 5. EXTINCTION
        System.out.println("🔌 Extinction des feux (Économie d'énergie !).");
        programmateur.shutdown();
    }
}

/*
Le détail technique à connaître pour l'examen
Si le professeur te demande pourquoi tu as utilisé scheduleAtFixedRate plutôt
que scheduleWithFixedDelay (l'autre méthode qui existe dans cette classe), voici la réponse parfaite :

scheduleAtFixedRate : Lance la tâche à un rythme strict (ex: toutes les 1 seconde pile),
peu importe le temps que la tâche met à s'exécuter. C'est parfait pour de la musique (Drums) ou des lumières synchronisées où le rythme est primordial.

scheduleWithFixedDelay : Attend que la tâche soit terminée, puis compte un délai de pause avant
de lancer la suivante. Le rythme n'est donc pas strict et se décale dans le temps.



 */