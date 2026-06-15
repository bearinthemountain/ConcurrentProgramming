package org.example.Exercices.CyclicBarrierPatrouilleDesGlaciers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
/*
2. Pourquoi l'exemple "Refuge" semble différent ?
La différence se joue uniquement dans le "Runnable de la barrière" (le deuxième argument du constructeur new CyclicBarrier(n, Runnable)).

Dans l'exemple du refuge : Le but est la continuation (repartir ensemble pour l'étape 2).

Dans l'exemple de la PDG : Le but est la finalisation (le classement est l'étape finale, on ne repart pas pour une nouvelle étape).
C'est une excellente observation ! Vous avez raison : si le mot Runnable n'apparaît pas explicitement dans le constructeur de votre barrière finishLine, c'est parce que vous utilisez une expression lambda (la syntaxe () -> { ... }).

Voici pourquoi le compilateur Java comprend qu'il s'agit d'un Runnable sans que vous ayez besoin de l'écrire :

1. Le "Typage par contexte" (Target Typing)
Le constructeur de CyclicBarrier est défini comme suit dans le code source de Java :
public CyclicBarrier(int parties, Runnable barrierAction)

Quand vous écrivez :

Java
new CyclicBarrier(20, () -> { ... });
Java voit que le deuxième argument attend un Runnable. Comme Runnable est une Interface Fonctionnelle (elle n'a qu'une seule méthode : run()), Java sait automatiquement que votre bloc () -> { ... } doit être transformé en l'implémentation de cette méthode run().

2. Comparaison avec votre exemple "Randonneur"
Dans votre exemple "Refuge", vous avez écrit :

Java
Runnable randonneur = () -> { ... };
Ici, vous avez explicitement déclaré le type Runnable. C'est comme si vous disiez : "Hé Java, voici une boîte nommée 'randonneur' qui contient un Runnable".

Dans le constructeur de la barrière :

Java
new CyclicBarrier(nb, () -> { ... });
Vous ne créez pas de variable intermédiaire, vous passez le bloc directement au constructeur. C'est comme si vous disiez : "Hé Java, voici directement le Runnable que le constructeur attend".

En résumé
Dans randonneur : Vous avez nommé le type (Runnable).

Dans CyclicBarrier : Le constructeur impose le type. Vous n'avez pas besoin de le répéter, Java le "devine" car c'est le seul type accepté à cet endroit.

C'est une syntaxe très courante en Java moderne pour alléger le code. Est-ce que cela vous rassure sur le fait que votre code est techniquement correct, même sans le mot-clé Runnable ?
 */
public class Main {
    private static int NUM_TEAMS=20;

    public static void main(String[] args) {
        List<Result> results = Collections.synchronizedList(new ArrayList<>());

        System.out.println("Starting PDG with" + NUM_TEAMS + "Teams");

        CyclicBarrier finishLine = new CyclicBarrier(NUM_TEAMS, () ->{
                System.out.println("Referee starts to establish ranking...");
                Collections.sort(results);
            Collections.sort(results);
            System.out.println("PDG Team Ranking :");
            for (Result r : results){
                System.out.println(r.getTeam() + ": " + r.getTime() + "h");
            }
        });

        for (int i = 0; i < NUM_TEAMS; i++) {
            final String teamName ="Team" + i;
            new Thread(() -> {
                /*
                c'est pareil que :
                new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Le thread travaille...");
                }
                        }).start();
                 */
                try {
                    System.out.println(teamName + "Started the race");
                    int time = (int) (Math.random()*30)+1;
                    Thread.sleep(100);
                    results.add(new Result(teamName,time));
                    System.out.println(teamName+" : Finished the race in "+ time + "hours");

                    finishLine.await();
            } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            }
            ).start();

        }

    }
}
