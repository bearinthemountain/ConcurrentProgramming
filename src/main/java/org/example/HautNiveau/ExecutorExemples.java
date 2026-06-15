package org.example.HautNiveau;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorExemples {

    public static void main(String[] args) {
        System.out.println("--- 1. Test EXECUTOR SIMPLE ---");
        exempleExecutorSimple();

        System.out.println("\n--- 2. Test EXECUTOR SERVICE ---");
        exempleExecutorService();

        System.out.println("\n--- 3. Test SCHEDULED EXECUTOR SERVICE ---");
        exempleScheduledExecutor();
    }

    // =========================================================================
    // 1. L'INTERFACE DE BASE : Executor
    // Rôle : Lancer des tâches (remplace le 'new Thread(tache).start()')
    // =========================================================================
    public static void exempleExecutorSimple() {
        // On crée un pool avec 1 seul ouvrier
        Executor executor = Executors.newSingleThreadExecutor();

        // On crée une tâche simple (Runnable)
        Runnable tache = () -> {
            System.out.println("Ouvrier : Je fais ma tâche simple !");
        };

        // On donne la tâche à l'executor via la méthode 'execute()'
        executor.execute(tache);

        // Note : L'interface de base Executor ne possède PAS de méthode pour s'éteindre !
        // Dans la vraie vie, on utilise donc presque toujours ExecutorService (voir en dessous).
    }

    // =========================================================================
    // 2. LE PLUS UTILISÉ : ExecutorService
    // Rôle : Gère le cycle de vie (s'éteindre proprement) et peut renvoyer des résultats
    // =========================================================================
    public static void exempleExecutorService() {
        // On crée un pool de 2 ouvriers réutilisables
        ExecutorService service = Executors.newFixedThreadPool(2);

        // On soumet 3 tâches. Les 2 ouvriers vont se les répartir automatiquement !
        for (int i = 1; i <= 3; i++) {
            final int numeroTache = i;
            service.submit(() -> {
                System.out.println(Thread.currentThread().getName() + " exécute la tâche " + numeroTache);
            });
        }

        // TRÈS IMPORTANT POUR L'EXAMEN : Le cycle de vie
        // Si tu ne fais pas shutdown(), le programme ne s'arrêtera JAMAIS,
        // car les ouvriers attendront de nouvelles tâches à l'infini.
        service.shutdown();
        System.out.println("Le service a reçu l'ordre de fermer (il finira les tâches en cours).");
    }

    // =========================================================================
    // 3. LE MINUTEUR : ScheduledExecutorService
    // Rôle : Lancer des tâches dans le futur (avec un délai) ou de façon répétitive
    // =========================================================================
    public static void exempleScheduledExecutor() {
        // On crée un ouvrier spécialisé dans les tâches planifiées
        ScheduledExecutorService minuteur = Executors.newScheduledThreadPool(1);

        Runnable tacheRetardee = () -> {
            System.out.println("BIP BIP ! Tâche exécutée après 2 secondes de délai.");
        };

        System.out.println("Lancement du chrono...");

        // Méthode clé pour l'examen : schedule (tâche, délai, unité de temps)
        minuteur.schedule(tacheRetardee, 2, TimeUnit.SECONDS);

        // On le ferme poliment (il attendra d'avoir fait sa tâche retardée pour mourir)
        minuteur.shutdown();
    }
}

/*
Tu as tout à fait raison sur la règle de base en Java : On ne peut JAMAIS faire un new directement sur une interface (faire new Executor() ferait planter le code instantanément). Il faut obligatoirement qu'une classe l'implémente.

Alors, d'où viennent ces objets magiques dans mon code ? La réponse tient en un seul mot (avec un "s" à la fin) : Executors.

Le secret : Le patron de conception "Fabrique" (Factory Pattern)
Dans mon code, je n'ai pas écrit new Executor(). J'ai écrit :
ExecutorService service = Executors.newFixedThreadPool(2);

La classe Executors (avec un 's') est ce qu'on appelle une Classe Usine (Factory class). C'est une classe utilitaire fournie par Java dont le seul but est de fabriquer des exécuteurs complexes à ta place, pour te cacher les détails affreux.

Voici ce qui se passe "en coulisses" quand tu appelles cette méthode :

Tu demandes à l'usine Executors : "Hé, donne-moi un pool de 2 ouvriers s'il te plaît !"

À l'intérieur de l'usine, Java va chercher une vraie classe (très complexe) cachée dans le système, qui s'appelle ThreadPoolExecutor (et qui, elle, a bien un implements ExecutorService dans son code source !).

L'usine configure cette grosse classe avec les bons paramètres, fait le vrai new ThreadPoolExecutor(...), et te la renvoie.

Toi, tu la réceptionnes dans une variable de type interface (ExecutorService).

Pourquoi Java fait-il ça ? (Pour te sauver la vie !)
Si la classe usine Executors n'existait pas, voici le monstre que tu serais obligé d'écrire toi-même pour créer un simple pool de 2 threads :

Java
// La version SANS l'usine Executors (L'enfer du bas niveau)
ExecutorService service = new ThreadPoolExecutor(
    2, // corePoolSize
    2, // maximumPoolSize
    0L, // keepAliveTime
    TimeUnit.MILLISECONDS, // unit
    new LinkedBlockingQueue<Runnable>() // workQueue
);


 */