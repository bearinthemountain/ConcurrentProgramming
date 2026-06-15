package org.example.BasNiveau;

// 1. LA RESSOURCE PARTAGÉE (Le cœur du Guarded Block)
// On la met en dehors de la classe principale pour que ce soit propre.
class BoiteAuxLettres {
    private String message;

    // Le "drapeau" (flag) qui sert de condition pour nos Guarded Blocks
    private boolean messageDisponible = false;

    // --- MÉTHODE POUR LE PRODUCTEUR ---
    public synchronized void deposer(String msg) {
        // LA GARDE : Tant qu'il y a déjà un message, on attend.
        while (messageDisponible) {
            try {
                wait(); // Le producteur lâche le verrou et s'endort
            } catch (InterruptedException e) { return; }
        }

        // Si on sort du while, c'est que la boite est vide. On travaille !
        this.message = msg;
        this.messageDisponible = true; // On change l'état du drapeau
        System.out.println("Producteur a déposé : " + msg);

        // LA NOTIFICATION : On réveille le consommateur
        notifyAll();
    }

    // --- MÉTHODE POUR LE CONSOMMATEUR ---
    public synchronized String lire() {
        // LA GARDE : Tant qu'il n'y a PAS de message, on attend.
        while (!messageDisponible) {
            try {
                wait(); // Le consommateur lâche le verrou et s'endort
            } catch (InterruptedException e) { return null; }
        }

        // Si on sort du while, c'est qu'il y a un message. On travaille !
        String msgRecu = this.message;
        this.messageDisponible = false; // On vide la boite
        System.out.println("Consommateur a lu   : " + msgRecu);

        // LA NOTIFICATION : On réveille le producteur pour lui dire qu'il y a de la place
        notifyAll();
        return msgRecu;
    }
}

// 2. LE PROGRAMME PRINCIPAL (Ta classe GuarderBlock)
public class GuarderBlock {

    public static void main(String[] args) {
        BoiteAuxLettres boite = new BoiteAuxLettres();

        // Le Thread Producteur
        Thread producteur = new Thread(() -> {
            String[] phrases = {"Message 1", "Message 2", "Message 3"};
            for (String p : phrases) {
                boite.deposer(p);
            }
        });

        // Le Thread Consommateur
        Thread consommateur = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                boite.lire();
            }
        });

        // On lance les deux threads
        consommateur.start();
        producteur.start();
    }
}