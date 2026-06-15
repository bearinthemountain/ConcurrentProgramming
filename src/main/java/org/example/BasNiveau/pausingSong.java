package org.example.BasNiveau;

public class pausingSong {
    public static void main(String args[]) throws InterruptedException {
        Runnable songTask = () -> {
            String importantIngo[] = {
                    "Georges Brassens (1964) - Les Copains d'abord Non, ce n'était pas le radeau ",
                    "De la Méduse, ce bateau ",
                    "Qu'on se le dise au fond des ports Dise au fond des ports ",
                    "Il naviguait en père peinard Sur la grand-mare des canards ",
                    "Et s'appelait les Copains d'abord Les Copains d'abord"
            };

            for (int i = 0; i < importantIngo.length; i++) {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("\n[Thread Chanteur] : Aïe ! J'ai été interrompu ! Je m'arrête là.");
                    return;
                }
                System.out.println(importantIngo[i]);
            }
        };
        Thread songThread = new Thread(songTask);
        songThread.start();
        Thread.sleep(3500);// main thread qui dort
        System.out.println("\n[Thread Main] : ça suffit le ukuléké, j'interromps le chanteur");
        songThread.interrupt();
    }
}