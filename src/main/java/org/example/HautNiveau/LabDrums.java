package org.example.HautNiveau;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LabDrums {

    public static void main(String[] args) {
        System.out.println("🥁 Lancement du concert...");

        // 1. CRÉATION DU SCHEDULER
        // On crée un pool avec 3 "ouvriers" car on a 3 instruments différents à jouer en même temps.
        ScheduledExecutorService chefDorchestre = Executors.newScheduledThreadPool(3);

        // 2. CRÉATION DES TÂCHES (Les instruments)
        // Chaque instrument est un Runnable simple.
        Runnable grosseCaisse = () -> System.out.println("BOOM  (Grosse caisse)");
        Runnable cymbale = () -> System.out.println("  Tss (Cymbale)");
        Runnable caisseClaire = () -> System.out.println("    TCHAK (Caisse claire)");

        // 3. PLANIFICATION (La méthode clé pour l'examen)
        // Paramètres de scheduleAtFixedRate : (Tâche, Délai initial avant le 1er coup, Période entre chaque coup, Unité de temps)

        // La grosse caisse tape toutes les 1000 millisecondes (1 seconde), dès le début.
        chefDorchestre.scheduleAtFixedRate(grosseCaisse, 0, 1000, TimeUnit.MILLISECONDS);

        // La cymbale tape très vite, toutes les 500 ms (2 fois par seconde).
        chefDorchestre.scheduleAtFixedRate(cymbale, 0, 500, TimeUnit.MILLISECONDS);

        // La caisse claire tape toutes les 1000 ms, MAIS elle commence avec 500 ms de décalage pour s'intercaler avec la grosse caisse !
        chefDorchestre.scheduleAtFixedRate(caisseClaire, 500, 1000, TimeUnit.MILLISECONDS);

        // 4. GESTION DU TEMPS DE L'APPLICATION
        // Le Main Thread (le programme principal) s'endort pendant 5 secondes pour laisser la musique jouer.
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 5. EXTINCTION (OBLIGATOIRE)
        // Si on ne fait pas shutdown, le programme continuera de jouer à l'infini.
        System.out.println("🛑 Fin du concert, on remballe !");
        chefDorchestre.shutdown();
    }
}