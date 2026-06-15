package org.example.HautNiveau;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class LabCallablesFutures {

    // 1. CRÉATION DE LA TÂCHE (Callable au lieu de Runnable)
    // On précise <Integer> car notre calcul va retourner un nombre entier
    static class TacheMultiplication implements Callable<Integer> {
        private final int a;
        private final int b;

        public TacheMultiplication(int a, int b) {
            this.a = a;
            this.b = b;
        }

        // La méthode magique : 'call' au lieu de 'run'
        @Override
        public Integer call() throws Exception { // Elle peut renvoyer une valeur ET des exceptions
            System.out.println("Ouvrier : Je commence le calcul de " + a + " * " + b + "...");
            Thread.sleep(2000); // On simule un calcul très difficile qui prend 2 secondes

            // Démonstration d'une Exception (demandé dans ton Lab)
            if (a < 0 || b < 0) {
                // Cette exception sera "capturée" et renvoyée dans le ticket (Future)
                throw new IllegalArgumentException("Désolé, je refuse de multiplier des nombres négatifs !");
            }

            return a * b; // On retourne le résultat !
        }
    }

    // 2. LE PROGRAMME PRINCIPAL
    public static void main(String[] args) {
        // On engage un ouvrier
        ExecutorService service = Executors.newSingleThreadExecutor();

        System.out.println("Chef : Je donne deux tâches à l'ouvrier.");

        // On crée nos tâches
        Callable<Integer> calculReussi = new TacheMultiplication(5, 10);
        Callable<Integer> calculEchec = new TacheMultiplication(-2, 5); // Va générer une erreur

        // ÉTAPE CLÉ : On soumet les tâches avec 'submit' (et non 'execute')
        // On reçoit immédiatement un ticket "Future" en échange, sans attendre la fin du calcul.
        Future<Integer> ticketReussi = service.submit(calculReussi);
        Future<Integer> ticketEchec = service.submit(calculEchec);

        System.out.println("Chef : J'ai mes tickets, je peux faire autre chose en attendant...");

        try {
            // On réclame le premier résultat
            System.out.println("Chef : J'attends le premier résultat...");

            // La méthode .get() BLOQUE le code jusqu'à ce que l'ouvrier ait fini (2 secondes ici)
            int resultat = ticketReussi.get();
            System.out.println("Chef : Le résultat est " + resultat);

            // On réclame le deuxième résultat (celui qui plante)
            System.out.println("Chef : J'attends le deuxième résultat...");
            int resultatPlantage = ticketEchec.get();
            System.out.println("Le résultat est " + resultatPlantage);

        } catch (InterruptedException e) {
            // Si le thread principal est interrompu pendant qu'il attend (get)
            System.out.println("L'attente a été interrompue.");

        } catch (ExecutionException e) {
            // SI LA TÂCHE CALLABLE A PLANTÉ, L'ERREUR RESSORT ICI !
            // C'est super puissant : l'erreur n'a pas fait crasher l'ouvrier dans son coin,
            // elle nous est proprement renvoyée dans le programme principal.
            System.out.println("Chef : Oups, la tâche a échoué. Cause : " + e.getCause().getMessage());
        } finally {
            // On n'oublie jamais de fermer l'entreprise
            service.shutdown();
        }
    }
}