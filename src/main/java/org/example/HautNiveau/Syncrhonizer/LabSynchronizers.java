package org.example.HautNiveau.Syncrhonizer;

/*
 Le Guide de Choix Rapide (Quel outil pour quel problème ?)
 Si l'exercice demande de...Utilise cet outilAnalogieLimiter
 l'accès à un nombre précis de places (ex: 3 places de parking,
 5 connexions DB).SemaphoreLe videur de boîte de nuit qui laisse entrer
 5 personnes max.Faire attendre le chef
 jusqu'à ce que N ouvriers aient fini leur tâche préparatoire
 (Usage unique).CountDownLatchLe décollage d'une fusée
 (On attend que les 5 voyants soient verts pour partir).
 Faire avancer un groupe de threads en "étapes".
 Ils doivent tous s'attendre à un point de contrôle avant de passer à
 l'étape suivante (Réutilisable).CyclicBarrierDes randonneurs qui s'attendent au
 refuge avant d'attaquer le sommet ensemble.Faire en sorte que 2 threads s'échangent
 des données au même instant T.ExchangerDeux espions qui échangent des mallettes dans une ruelle.
 Synchroniser des threads sur plusieurs phases, avec un nombre de threads qui peut changer en cours de route (
 Complexe).PhaserUne chaîne de montage complexe où des ouvriers arrivent et partent selon les phases./*
 */



import java.util.concurrent.*;

public class LabSynchronizers {

    public static void main(String[] args) throws InterruptedException {
        // Décommente la méthode que tu as besoin de tester :

        exempleSemaphore();
        // exempleCountDownLatch();
        // exempleCyclicBarrier();
        // exempleExchanger();
        // exemplePhaser();
    }

    // ==========================================================
    // 1. SEMAPHORE : Limiter le nombre d'accès simultanés
    // ==========================================================
    public static void exempleSemaphore() {
        System.out.println("--- SEMAPHORE ---");
        // On crée un péage avec seulement 2 voies ouvertes (2 permits)
        Semaphore peage = new Semaphore(2);

        Runnable voiture = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + " attend au péage...");
                peage.acquire(); // Prend un ticket (bloque s'il n'y en a plus)

                System.out.println("🟢 " + Thread.currentThread().getName() + " passe le péage !");
                Thread.sleep(1500); // Temps de passage

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                System.out.println("🔴 " + Thread.currentThread().getName() + " quitte le péage.");
                peage.release(); // REND le ticket (OBLIGATOIRE dans le finally)
            }
        };

        // On lance 5 voitures. Seules 2 passeront à la fois.
        for (int i = 1; i <= 5; i++) new Thread(voiture, "Voiture-" + i).start();
    }


    // ==========================================================
    // 2. COUNTDOWN LATCH : Attendre que N tâches soient finies
    // ==========================================================
    public static void exempleCountDownLatch() throws InterruptedException {
        System.out.println("--- COUNTDOWN LATCH ---");
        int nbTaches = 3;
        // On initialise le compteur à 3
        CountDownLatch loquet = new CountDownLatch(nbTaches);

        Runnable serviceInitialisation = () -> {
            try {
                Thread.sleep((long) (Math.random() * 2000));
                System.out.println("✅ " + Thread.currentThread().getName() + " a terminé son initialisation.");
            } catch (InterruptedException e) {}
            finally {
                loquet.countDown(); // DÉCRÉMENTE le compteur (3 -> 2 -> 1 -> 0)
            }
        };

        new Thread(serviceInitialisation, "Base de données").start();
        new Thread(serviceInitialisation, "Réseau").start();
        new Thread(serviceInitialisation, "Interface Web").start();

        System.out.println("Chef : J'attends que les 3 services démarrent...");
        loquet.await(); // BLOQUE le Main jusqu'à ce que le compteur atteigne 0
        System.out.println("Chef : Tout est à 0 ! L'application est prête, on y va !");
    }


    // ==========================================================
    // 3. CYCLIC BARRIER : Point de rassemblement de N threads
    // ==========================================================
    public static void exempleCyclicBarrier() {
        System.out.println("--- CYCLIC BARRIER ---");
        int nbRandonneurs = 3;

        // La barrière attend 3 threads. Quand les 3 sont là, elle exécute le Runnable fourni.
        CyclicBarrier refuge = new CyclicBarrier(nbRandonneurs, () -> {
            System.out.println("⛺ TOUT LE MONDE EST AU REFUGE ! On attaque l'étape 2 ensemble.");
        });

        Runnable randonneur = () -> {
            try {
                System.out.println("🚶 " + Thread.currentThread().getName() + " commence l'étape 1.");
                Thread.sleep((long) (Math.random() * 3000));
                System.out.println("⏳ " + Thread.currentThread().getName() + " est arrivé au refuge et attend les autres.");

                refuge.await(); // Le thread se bloque et attend les autres ici.

                System.out.println("🧗 " + Thread.currentThread().getName() + " commence l'étape 2.");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        };

        for (int i = 1; i <= nbRandonneurs; i++) new Thread(randonneur, "Rando-" + i).start();
    }


    // ==========================================================
    // 4. EXCHANGER : Échange de données entre DEUX threads
    // ==========================================================
    public static void exempleExchanger() {
        System.out.println("--- EXCHANGER ---");
        // Un exchanger qui échange des objets de type String
        Exchanger<String> ruelleSombre = new Exchanger<>();

        new Thread(() -> {
            try {
                String monInfo = "Code Nucléaire";
                System.out.println("Espion 1 : J'arrive avec -> " + monInfo);
                // Bloque jusqu'à ce que l'espion 2 arrive, puis retourne l'objet de l'espion 2
                String infoRecue = ruelleSombre.exchange(monInfo);
                System.out.println("Espion 1 : Je repars avec -> " + infoRecue);
            } catch (InterruptedException e) {}
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(2000); // L'espion 2 est en retard
                String maMallette = "Argent en liquide";
                System.out.println("Espion 2 : J'arrive enfin avec -> " + maMallette);
                String infoRecue = ruelleSombre.exchange(maMallette);
                System.out.println("Espion 2 : Je repars avec -> " + infoRecue);
            } catch (InterruptedException e) {}
        }).start();
    }


    // ==========================================================
    // 5. PHASER : Synchronisation en plusieurs phases (Complexe)
    // ==========================================================
    public static void exemplePhaser() {
        System.out.println("--- PHASER ---");
        Phaser phaser = new Phaser(1); // Le thread Main est le 1er inscrit

        Runnable ouvrier = () -> {
            phaser.register(); // Un nouvel ouvrier s'inscrit au Phaser

            // Phase 1
            System.out.println("🛠️ " + Thread.currentThread().getName() + " fait le travail de la Phase 1");
            phaser.arriveAndAwaitAdvance(); // J'ai fini la phase 1, j'attends les autres

            // Phase 2
            System.out.println("🎨 " + Thread.currentThread().getName() + " fait le travail de la Phase 2");
            phaser.arriveAndDeregister(); // J'ai fini la phase 2, je pars de l'usine
        };

        new Thread(ouvrier, "Ouvrier A").start();
        new Thread(ouvrier, "Ouvrier B").start();

        // Le Main thread gère le passage des phases
        System.out.println("Chef : Tout le monde est prêt pour la Phase 1 ?");
        phaser.arriveAndAwaitAdvance(); // Lance la phase 1

        System.out.println("Chef : Phase 1 terminée ! Début de la Phase 2.");
        phaser.arriveAndAwaitAdvance(); // Lance la phase 2

        System.out.println("Chef : Travail terminé.");
        phaser.arriveAndDeregister(); // Le chef part
    }
}

/*
C'est la question piège classique. Les deux se ressemblent (attendre N threads).
La différence est que le CountDownLatch est à usage unique (quand le compteur arrive à 0, il y reste pour toujours).
La CyclicBarrier est réutilisable
(quand tous les threads sont passés, la barrière se referme et est prête pour un nouveau cycle).

 */