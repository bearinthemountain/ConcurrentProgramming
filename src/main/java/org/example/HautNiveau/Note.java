package org.example.HautNiveau;

// Il faut importer ces 3 classes pour utiliser les Locks modernes
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

public class Note { // J'ai mis une majuscule à Note (convention Java)
    /*
    D'ailleurs, si tu regardes le sous-titre de ta diapo,
     il indique "Lock Objects".
     Tu te souviens du ReentrantLock que nous avons utilisé tout à l'heure pour résoudre
     le Livelock et la Starvation (Famine) ? Eh bien, c'est exactement ça !
     Tu as déjà un pied dans le haut niveau sans le savoir.
     Les Lock Objects sont les remplaçants modernes et beaucoup plus
     intelligents du bon vieux mot-clé synchronized.

     Même principe de base :
     Les objets Lock font exactement le même travail que le mot-clé synchronized.
     Un seul thread à la fois peut détenir la "clé" (le verrou).

    Système d'attente (wait/notify) :
    Ils permettent aussi aux threads de se mettre en pause et de se réveiller,
    mais au lieu d'utiliser wait() et notifyAll(), ils utilisent des objets spécifiques appelés Condition.

    Le super-pouvoir (L'avantage principal) :
    C'est la grande différence avec synchronized !
    Un objet Lock permet à un thread de "faire marche arrière".
    Grâce à la méthode tryLock(), un thread peut essayer d'ouvrir une porte,
    et si elle est fermée, il peut abandonner immédiatement ou attendre un temps
    limite défini (timeout) au lieu de rester bloqué là pour l'éternité.

    Avant de voir les exemples, voici la règle d'or absolue (à écrire en gros sur ta copie) :
     Toute acquisition de Lock DOIT être suivie d'un unlock() dans un bloc finally.
     Si une exception survient pendant que le thread a le verrou et qu'il n'y a pas de finally,
      le verrou ne sera jamais rendu et ton programme plantera (Deadlock).
     */

    // On englobe tous les tests dans la méthode main
    public static void main(String[] args) {

        // On instancie le verrou ici pour pouvoir s'en servir en dessous
        Lock verrou = new ReentrantLock();

        System.out.println("--- Test 1 : lock() ---");
        verrou.lock(); // Attente infinie si occupé
        try {
            // Opération critique garantie (ex: solde = solde + 100)
            System.out.println("Opération effectuée.");
        } finally {
            verrou.unlock(); // On rend la clé quoi qu'il arrive
        }

        System.out.println("\n--- Test 2 : lockInterruptibly() ---");
        try {
            verrou.lockInterruptibly(); // Attente, mais sensible aux interruptions
            try {
                // Opération annulable
                System.out.println("Opération sensible aux interruptions effectuée.");
            } finally {
                verrou.unlock();
            }
        } catch (InterruptedException e) {
            System.out.println("Attente annulée par l'utilisateur !");
        }

        System.out.println("\n--- Test 3 : tryLock() ---");
        if (verrou.tryLock()) { // Essaie de prendre, retourne true si succès
            try {
                System.out.println("Super, c'était libre !");
            } finally {
                verrou.unlock();
            }
        } else {
            System.out.println("C'est occupé, pas grave je vais faire autre chose.");
            // Fait un autre travail sans bloquer
        }

        System.out.println("\n--- Test 4 : tryLock avec timeout ---");
        try {
            // Essaie d'acquérir le verrou pendant 2 secondes maximum
            if (verrou.tryLock(2, TimeUnit.SECONDS)) {
                try {
                    System.out.println("J'ai eu le verrou de justesse !");
                } finally {
                    verrou.unlock();
                }
            } else {
                System.out.println("Trop long ! J'abandonne après 2 secondes d'attente.");
            }
        } catch (InterruptedException e) {
            System.out.println("Quelqu'un m'a interrompu pendant mes 2 secondes d'attente.");
        }
    }
}