package org.example.Exercices.PhaserNextFamilyBikeTripe;

import java.util.concurrent.Phaser;

public class Main {
    public static void main(String[] args) {
        String[] family = {"Father", "Mother", "Son", "Daughter_1", "Daughter_2"};
        String[] routes = {"Sierre", "Sion", "Martigny", "St-Maurice", "Aigle", "Vevey"};
// On initialise le phaser avec 1 (le main thread)
        Phaser phaser = new Phaser(1);

        for (String member : family) {
            phaser.register(); // On enregistre chaque membre comme participant

            new Thread(() -> {
                // Pour chaque route
                for (String route : routes) {
                    System.out.println(member + "roule jusqu'à " + route);

                    try {
                        // Simulation du temps de trajet
                        Thread.sleep((long)(Math.random() * 1000));} catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    // Arrive et attend que les autres membres aient atteint la même ville
                    phaser.arriveAndAwaitAdvance();
                    }
                // Une fois fini, on se désinscrit
                phaser.arriveAndDeregister();

                }, member).start();
            }
        // Le main thread attend aussi à chaque étape
        for (String route : routes) {
            phaser.arriveAndAwaitAdvance();
            System.out.println("---Tout le monde est arrivé à ---" + route + "---");
        }
        }
}
/*
Le main comme participant : En initialisant avec new Phaser(1),
le main est considéré comme un participant.
C'est pour cela qu'il doit aussi faire arriveAndAwaitAdvance()
 dans sa propre boucle pour permettre à la barrière de se débloquer.
 Si vous ne mettez pas le main comme participant, il pourrait se terminer alors que les threads de la famille sont encore en train de rouler sur la route.

Sans main dans le Phaser : Le main lance les threads et se termine immédiatement après la boucle for. Le programme s'arrête brutalement, tuant peut-être les threads en plein milieu de leur travail.

Avec main dans le Phaser : Le main est "bloqué" à chaque phaser.arriveAndAwaitAdvance(). Il ne peut pas passer à l'étape suivante (ou terminer le programme) tant que toute la famille n'est pas arrivée au point de rendez-vous.

2. Synchronisation "Phase par Phase"
En incluant le main, vous pouvez orchestrer des actions que seul le thread principal doit faire entre deux étapes, comme :

Afficher un message de résumé ("Tout le monde est arrivé à Sion, pause boisson !").

Vérifier l'état global du système.

Modifier dynamiquement la configuration du Phaser pour l'étape suivante.

3. Gestion élégante de la fin du programme
Dans l'exemple du vélo, vous voulez que le message final ("Le voyage est terminé") soit affiché par le main uniquement quand tout le monde a terminé.
Si le main n'est pas participant, il ne sait pas "quand" la famille a fini son dernier trajet. En étant participant, il est libéré de son blocage await() au moment précis où le dernier membre arrive à la destination finale.
 */